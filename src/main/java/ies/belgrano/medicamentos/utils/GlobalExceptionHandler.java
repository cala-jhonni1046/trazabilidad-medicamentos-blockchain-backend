package ies.belgrano.medicamentos.utils;

import java.time.Instant;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import ies.belgrano.medicamentos.exceptions.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex,
            WebRequest request) {
        List<ErrorResponseDTO.FieldError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorResponseDTO.FieldError(error.getField(), error.getDefaultMessage()))
                .toList();
        return response(HttpStatus.BAD_REQUEST, ApiErrorCode.VALIDATION_ERROR, "Error de validación", errors, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex,
            WebRequest request) {
        return response(HttpStatus.NOT_FOUND, ApiErrorCode.RESOURCE_NOT_FOUND, ex.getMessage(), null, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException ex,
            WebRequest request) {
        return response(HttpStatus.CONFLICT, ApiErrorCode.RESOURCE_CONFLICT,
                "La operación entra en conflicto con los datos existentes.", null, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        return response(HttpStatus.BAD_REQUEST, ApiErrorCode.INVALID_ARGUMENT, "La solicitud contiene un valor inválido.", null,
                request);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalState(IllegalStateException ex, WebRequest request) {
        return response(HttpStatus.CONFLICT, ApiErrorCode.STATE_CONFLICT,
                "La operación no está permitida para el estado actual del recurso.", null, request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, WebRequest request) {
        return response(HttpStatus.BAD_REQUEST, ApiErrorCode.INVALID_ARGUMENT,
                "El cuerpo de la solicitud no es legible o contiene un formato de datos incorrecto.", null, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        return response(HttpStatus.BAD_REQUEST, ApiErrorCode.INVALID_ARGUMENT,
                "Parámetro de solicitud inválido o con tipo de dato no admitido.", null, request);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, WebRequest request) {
        return response(HttpStatus.BAD_REQUEST, ApiErrorCode.INVALID_ARGUMENT,
                "Falta un parámetro requerido en la solicitud.", null, request);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoResourceFound(
            NoResourceFoundException ex, WebRequest request) {
        return response(HttpStatus.NOT_FOUND, ApiErrorCode.RESOURCE_NOT_FOUND,
                "Recurso no encontrado o ruta inexistente.", null, request);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, WebRequest request) {
        return response(HttpStatus.METHOD_NOT_ALLOWED, ApiErrorCode.INVALID_ARGUMENT,
                "Método HTTP no permitido para este recurso.", null, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneralException(Exception ex, WebRequest request) {
        return response(HttpStatus.INTERNAL_SERVER_ERROR, ApiErrorCode.INTERNAL_ERROR,
                "Ocurrió un error interno en el servidor.", null, request);
    }

    private ResponseEntity<ErrorResponseDTO> response(HttpStatus status, ApiErrorCode code, String message,
            List<ErrorResponseDTO.FieldError> fieldErrors, WebRequest request) {
        String path = request instanceof ServletWebRequest servletRequest
                ? servletRequest.getRequest().getRequestURI() : null;
        ErrorResponseDTO body = new ErrorResponseDTO(Instant.now(), status.value(), code.name(), message, path, fieldErrors);
        return ResponseEntity.status(status).body(body);
    }
}
