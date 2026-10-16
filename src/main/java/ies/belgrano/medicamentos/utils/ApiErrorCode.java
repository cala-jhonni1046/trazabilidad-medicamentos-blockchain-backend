package ies.belgrano.medicamentos.utils;

/** Stable, transport-level error identifiers shared by all API modules. */
public enum ApiErrorCode {
    VALIDATION_ERROR,
    RESOURCE_NOT_FOUND,
    RESOURCE_CONFLICT,
    INVALID_ARGUMENT,
    STATE_CONFLICT,
    UNAUTHORIZED,
    FORBIDDEN,
    INTERNAL_ERROR
}
