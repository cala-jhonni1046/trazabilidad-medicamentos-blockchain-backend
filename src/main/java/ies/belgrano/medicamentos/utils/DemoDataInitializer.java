package ies.belgrano.medicamentos.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ies.belgrano.medicamentos.enlaceCUIT.EnlaceCUIT;
import ies.belgrano.medicamentos.enlaceCUIT.EnlaceCUITRepository;
import ies.belgrano.medicamentos.enlaceCUIT.EstadoEnlaceCuit;
import ies.belgrano.medicamentos.inspector.EstadoInspector;
import ies.belgrano.medicamentos.inspector.InspectorANMAT;
import ies.belgrano.medicamentos.inspector.InspectorANMATRepository;
import ies.belgrano.medicamentos.medicamento.Medicamento;
import ies.belgrano.medicamentos.medicamento.MedicamentoRepository;
import ies.belgrano.medicamentos.organizacion.Organizacion;
import ies.belgrano.medicamentos.organizacion.OrganizacionRepository;
import ies.belgrano.medicamentos.organizacion.TipoOrganizacion;
import ies.belgrano.medicamentos.usuario.RolUsuario;
import ies.belgrano.medicamentos.usuario.Usuario;
import ies.belgrano.medicamentos.usuario.UsuarioRepository;

/**
 * Sembrador de datos demostrativos totalmente ficticios para pruebas de interfaz y flujos sin pasos previos.
 * Solo se activa bajo el perfil 'demo'. Totalmente idempotente.
 */
@Component
@Profile("demo")
public class DemoDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DemoDataInitializer.class);

    private final UsuarioRepository usuarioRepo;
    private final InspectorANMATRepository inspectorRepo;
    private final OrganizacionRepository organizacionRepo;
    private final MedicamentoRepository medicamentoRepo;
    private final EnlaceCUITRepository enlaceRepo;

    public DemoDataInitializer(UsuarioRepository usuarioRepo,
                                InspectorANMATRepository inspectorRepo,
                                OrganizacionRepository organizacionRepo,
                                MedicamentoRepository medicamentoRepo,
                                EnlaceCUITRepository enlaceRepo) {
        this.usuarioRepo = usuarioRepo;
        this.inspectorRepo = inspectorRepo;
        this.organizacionRepo = organizacionRepo;
        this.medicamentoRepo = medicamentoRepo;
        this.enlaceRepo = enlaceRepo;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        log.info("Iniciando sembrado de datos ficticios bajo perfil 'demo'...");

        int creados = 0;
        int yaExistian = 0;

        // 1. Usuarios activos por cada rol
        UsuarioResult resSuperadmin = getOrSaveUser("superadmin@medichain.demo", RolUsuario.SUPERADMIN);
        if (resSuperadmin.created) creados++; else yaExistian++;

        UsuarioResult resInspector = getOrSaveUser("inspector@medichain.demo", RolUsuario.ANMAT);
        if (resInspector.created) creados++; else yaExistian++;

        UsuarioResult resLab = getOrSaveUser("laboratorio@medichain.demo", RolUsuario.LABORATORIO);
        if (resLab.created) creados++; else yaExistian++;

        UsuarioResult resDist = getOrSaveUser("distribuidor@medichain.demo", RolUsuario.DISTRIBUIDOR);
        if (resDist.created) creados++; else yaExistian++;

        UsuarioResult resFarm = getOrSaveUser("farmacia@medichain.demo", RolUsuario.FARMACIA);
        if (resFarm.created) creados++; else yaExistian++;

        UsuarioResult resPaciente = getOrSaveUser("paciente@medichain.demo", RolUsuario.PACIENTE);
        if (resPaciente.created) creados++; else yaExistian++;

        // 2. Inspector ANMAT HABILITADO vinculado al usuario ANMAT
        String legajoDemo = "LEG-DEMO-001";
        InspectorANMAT inspector = inspectorRepo.findByLegajoOficial(legajoDemo)
                .or(() -> inspectorRepo.findByUsuarioId(resInspector.user.getId()))
                .orElse(null);
        if (inspector == null) {
            inspector = new InspectorANMAT();
            inspector.setUsuario(resInspector.user);
            inspector.setLegajoOficial(legajoDemo);
            inspector.setNombreCompleto("Auditor Sanitario Ficticio");
            inspector.setDni("11222333");
            inspector.setEmailInstitucional("auditor@medichain.demo");
            inspector.setJefeAutorizador("Dirección de Fiscalización Ficticia");
            inspector.setEstado(EstadoInspector.HABILITADO);
            inspector.setFechaHoraAlta(LocalDateTime.now(ZoneOffset.UTC));
            inspector = inspectorRepo.save(inspector);
            creados++;
        } else {
            yaExistian++;
        }

        // 3. Tres Organizaciones ficticias (11 dígitos, inventadas)
        OrgResult resOrgLab = getOrSaveOrg("30111111118", "Laboratorio Demo Medichain Lab", TipoOrganizacion.LABORATORIO);
        if (resOrgLab.created) creados++; else yaExistian++;

        OrgResult resOrgDist = getOrSaveOrg("30222222228", "Distribuidora Demo Logística Express", TipoOrganizacion.DISTRIBUIDOR);
        if (resOrgDist.created) creados++; else yaExistian++;

        OrgResult resOrgFarm = getOrSaveOrg("30333333338", "Farmacia Central Demo", TipoOrganizacion.FARMACIA);
        if (resOrgFarm.created) creados++; else yaExistian++;

        // Dos organizaciones adicionales para el enlace pendiente (demostración de aprobación en vivo)
        OrgResult resOrgLab2 = getOrSaveOrg("30444444448", "Laboratorio Innovación Demo", TipoOrganizacion.LABORATORIO);
        if (resOrgLab2.created) creados++; else yaExistian++;

        OrgResult resOrgFarm2 = getOrSaveOrg("30555555558", "Farmacia Sanitaria Demo", TipoOrganizacion.FARMACIA);
        if (resOrgFarm2.created) creados++; else yaExistian++;

        // 4. Medicamento ficticio con requiereCadenaFrio=true
        String medNombre = "Vacuna Demostrativa Termosensible 10ml";
        Medicamento med = medicamentoRepo.findByNombre(medNombre).orElse(null);
        if (med == null) {
            med = new Medicamento();
            med.setNombre(medNombre);
            med.setPrincipioActivo("Antígeno Ficticio Demo");
            med.setRequiereCadenaFrio(true);
            medicamentoRepo.save(med);
            creados++;
        } else {
            yaExistian++;
        }

        // 5. EnlaceCUIT ACTIVO entre Lab 1, Dist 1 y Farm 1 aprobado por el inspector sembrado
        EnlaceCUIT enlaceActivo = enlaceRepo.findByLaboratorioIdAndDistribuidorIdAndFarmaciaId(
                resOrgLab.org.getId(), resOrgDist.org.getId(), resOrgFarm.org.getId()).orElse(null);
        if (enlaceActivo == null) {
            enlaceActivo = new EnlaceCUIT();
            enlaceActivo.setLaboratorio(resOrgLab.org);
            enlaceActivo.setDistribuidor(resOrgDist.org);
            enlaceActivo.setFarmacia(resOrgFarm.org);
            enlaceActivo.setEstado(EstadoEnlaceCuit.ACTIVO);
            enlaceActivo.setInspectorAprobador(inspector);
            enlaceActivo.setNumeroDisposicionANMAT("DISP-DEMO-2026-001");
            enlaceActivo.setDocumentoCertificadoUrl("https://demo.medichain.local/certificados/disp-001.pdf");
            enlaceActivo.setFechaSolicitud(LocalDateTime.now(ZoneOffset.UTC).minusDays(5));
            enlaceActivo.setFechaAprobacionANMAT(LocalDateTime.now(ZoneOffset.UTC).minusDays(2));
            enlaceRepo.save(enlaceActivo);
            creados++;
        } else {
            yaExistian++;
        }

        // 6. EnlaceCUIT PENDIENTE entre Lab 2, Dist 1 y Farm 2 (sin inspector ni fecha de aprobación)
        EnlaceCUIT enlacePendiente = enlaceRepo.findByLaboratorioIdAndDistribuidorIdAndFarmaciaId(
                resOrgLab2.org.getId(), resOrgDist.org.getId(), resOrgFarm2.org.getId()).orElse(null);
        if (enlacePendiente == null) {
            enlacePendiente = new EnlaceCUIT();
            enlacePendiente.setLaboratorio(resOrgLab2.org);
            enlacePendiente.setDistribuidor(resOrgDist.org);
            enlacePendiente.setFarmacia(resOrgFarm2.org);
            enlacePendiente.setEstado(EstadoEnlaceCuit.PENDIENTE);
            enlacePendiente.setInspectorAprobador(null);
            enlacePendiente.setNumeroDisposicionANMAT(null);
            enlacePendiente.setDocumentoCertificadoUrl("https://demo.medichain.local/certificados/solicitud-demo-002.pdf");
            enlacePendiente.setFechaSolicitud(LocalDateTime.now(ZoneOffset.UTC));
            enlacePendiente.setFechaAprobacionANMAT(null);
            enlaceRepo.save(enlacePendiente);
            creados++;
        } else {
            yaExistian++;
        }

        log.info("DemoDataInitializer finalizado exitosamente. Registros creados: {}, Registros ya existentes: {}", creados, yaExistian);
    }

    private record UsuarioResult(Usuario user, boolean created) {}
    private record OrgResult(Organizacion org, boolean created) {}

    private UsuarioResult getOrSaveUser(String email, RolUsuario rol) {
        return usuarioRepo.findByEmail(email)
                .map(u -> new UsuarioResult(u, false))
                .orElseGet(() -> {
                    Usuario u = new Usuario();
                    u.setEmail(email);
                    u.setRol(rol);
                    u.setActivo(true);
                    return new UsuarioResult(usuarioRepo.save(u), true);
                });
    }

    private OrgResult getOrSaveOrg(String cuit, String nombre, TipoOrganizacion tipo) {
        return organizacionRepo.findByCuit(cuit)
                .map(o -> new OrgResult(o, false))
                .orElseGet(() -> {
                    Organizacion o = new Organizacion();
                    o.setCuit(cuit);
                    o.setNombre(nombre);
                    o.setTipo(tipo);
                    return new OrgResult(organizacionRepo.save(o), true);
                });
    }
}
