package ies.belgrano.medicamentos.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
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
 * Contraseña para todos los usuarios: Demo1234!
 */
@Component
@Profile("demo")
public class DemoDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DemoDataInitializer.class);
    private static final String PASS_DEMO = "Demo1234!";

    private final UsuarioRepository usuarioRepo;
    private final InspectorANMATRepository inspectorRepo;
    private final OrganizacionRepository organizacionRepo;
    private final MedicamentoRepository medicamentoRepo;
    private final EnlaceCUITRepository enlaceRepo;
    private final PasswordEncoder passwordEncoder;

    public DemoDataInitializer(UsuarioRepository usuarioRepo,
                                InspectorANMATRepository inspectorRepo,
                                OrganizacionRepository organizacionRepo,
                                MedicamentoRepository medicamentoRepo,
                                EnlaceCUITRepository enlaceRepo,
                                PasswordEncoder passwordEncoder) {
        this.usuarioRepo = usuarioRepo;
        this.inspectorRepo = inspectorRepo;
        this.organizacionRepo = organizacionRepo;
        this.medicamentoRepo = medicamentoRepo;
        this.enlaceRepo = enlaceRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        log.info("Iniciando sembrado de datos ficticios bajo perfil 'demo'...");

        int creados = 0;
        int yaExistian = 0;

        // ── Organizaciones ──────────────────────────────────────────────────────────
        OrgResult orgLab1  = getOrSaveOrg("30111111118", "Laboratorio Demo Medichain Lab",      TipoOrganizacion.LABORATORIO);
        OrgResult orgLab2  = getOrSaveOrg("30444444448", "Laboratorio Innovación Demo",         TipoOrganizacion.LABORATORIO);
        OrgResult orgDist1 = getOrSaveOrg("30222222228", "Distribuidora Demo Logística Express", TipoOrganizacion.DISTRIBUIDOR);
        OrgResult orgFarm1 = getOrSaveOrg("30333333338", "Farmacia Central Demo",               TipoOrganizacion.FARMACIA);
        OrgResult orgFarm2 = getOrSaveOrg("30555555558", "Farmacia Sanitaria Demo",             TipoOrganizacion.FARMACIA);

        creados   += (orgLab1.created ? 1:0) + (orgLab2.created ? 1:0) + (orgDist1.created ? 1:0)
                   + (orgFarm1.created ? 1:0) + (orgFarm2.created ? 1:0);
        yaExistian += (!orgLab1.created ? 1:0) + (!orgLab2.created ? 1:0) + (!orgDist1.created ? 1:0)
                   + (!orgFarm1.created ? 1:0) + (!orgFarm2.created ? 1:0);

        // ── Usuarios ─────────────────────────────────────────────────────────────────
        // Sin organización (roles globales)
        UsuarioResult resSuperadmin = getOrSaveUser("superadmin@medichain.demo", RolUsuario.SUPERADMIN, null);
        UsuarioResult resInspector  = getOrSaveUser("inspector@medichain.demo",  RolUsuario.ANMAT,       null);
        UsuarioResult resPaciente   = getOrSaveUser("paciente@medichain.demo",   RolUsuario.PACIENTE,    null);

        // Con organización (roles de empresa) — LAB 1
        UsuarioResult resLab1   = getOrSaveUser("laboratorio@medichain.demo",   RolUsuario.LABORATORIO, orgLab1.org);
        // LAB 2 (para probar aislamiento)
        UsuarioResult resLab2   = getOrSaveUser("laboratorio2@medichain.demo",  RolUsuario.LABORATORIO, orgLab2.org);
        // DISTRIBUIDOR
        UsuarioResult resDist1  = getOrSaveUser("distribuidor@medichain.demo",  RolUsuario.DISTRIBUIDOR, orgDist1.org);
        // FARMACIA 1
        UsuarioResult resFarm1  = getOrSaveUser("farmacia@medichain.demo",      RolUsuario.FARMACIA,     orgFarm1.org);
        // FARMACIA 2 (para probar aislamiento)
        UsuarioResult resFarm2  = getOrSaveUser("farmacia2@medichain.demo",     RolUsuario.FARMACIA,     orgFarm2.org);

        creados   += countCreados(resSuperadmin, resInspector, resPaciente, resLab1, resLab2, resDist1, resFarm1, resFarm2);
        yaExistian += countExistian(resSuperadmin, resInspector, resPaciente, resLab1, resLab2, resDist1, resFarm1, resFarm2);

        // ── Inspector ANMAT ──────────────────────────────────────────────────────────
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

        // ── Medicamento ───────────────────────────────────────────────────────────────
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

        // ── EnlaceCUIT ACTIVO (Lab1 ↔ Dist1 ↔ Farm1) ───────────────────────────────
        EnlaceCUIT enlaceActivo = enlaceRepo.findByLaboratorioIdAndDistribuidorIdAndFarmaciaId(
                orgLab1.org.getId(), orgDist1.org.getId(), orgFarm1.org.getId()).orElse(null);
        if (enlaceActivo == null) {
            enlaceActivo = new EnlaceCUIT();
            enlaceActivo.setLaboratorio(orgLab1.org);
            enlaceActivo.setDistribuidor(orgDist1.org);
            enlaceActivo.setFarmacia(orgFarm1.org);
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

        // ── EnlaceCUIT PENDIENTE (Lab2 ↔ Dist1 ↔ Farm2) ────────────────────────────
        EnlaceCUIT enlacePendiente = enlaceRepo.findByLaboratorioIdAndDistribuidorIdAndFarmaciaId(
                orgLab2.org.getId(), orgDist1.org.getId(), orgFarm2.org.getId()).orElse(null);
        if (enlacePendiente == null) {
            enlacePendiente = new EnlaceCUIT();
            enlacePendiente.setLaboratorio(orgLab2.org);
            enlacePendiente.setDistribuidor(orgDist1.org);
            enlacePendiente.setFarmacia(orgFarm2.org);
            enlacePendiente.setEstado(EstadoEnlaceCuit.PENDIENTE);
            enlacePendiente.setInspectorAprobador(null);
            enlacePendiente.setDocumentoCertificadoUrl("https://demo.medichain.local/certificados/solicitud-demo-002.pdf");
            enlacePendiente.setFechaSolicitud(LocalDateTime.now(ZoneOffset.UTC));
            enlaceRepo.save(enlacePendiente);
            creados++;
        } else {
            yaExistian++;
        }

        log.info("DemoDataInitializer finalizado. Registros creados: {}, Registros ya existentes: {}", creados, yaExistian);
        log.info("Credenciales demo: contraseña '{}' para todos los usuarios", PASS_DEMO);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────────

    private record UsuarioResult(Usuario user, boolean created) {}
    private record OrgResult(Organizacion org, boolean created) {}

    private UsuarioResult getOrSaveUser(String email, RolUsuario rol, Organizacion organizacion) {
        return usuarioRepo.findByEmail(email)
                .map(u -> {
                    // Usuarios sembrados antes del login real: les falta contraseña u organización
                    boolean cambio = false;
                    if (u.getPasswordHash() == null || u.getPasswordHash().isBlank()) {
                        u.setPasswordHash(passwordEncoder.encode(PASS_DEMO));
                        cambio = true;
                    }
                    if (u.getOrganizacion() == null && organizacion != null) {
                        u.setOrganizacion(organizacion);
                        cambio = true;
                    }
                    return new UsuarioResult(cambio ? usuarioRepo.save(u) : u, false);
                })
                .orElseGet(() -> {
                    Usuario u = new Usuario();
                    u.setEmail(email);
                    u.setRol(rol);
                    u.setActivo(true);
                    u.setPasswordHash(passwordEncoder.encode(PASS_DEMO));
                    u.setOrganizacion(organizacion);
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

    @SafeVarargs
    private <T extends UsuarioResult> int countCreados(T... results) {
        int c = 0;
        for (T r : results) if (r.created()) c++;
        return c;
    }

    @SafeVarargs
    private <T extends UsuarioResult> int countExistian(T... results) {
        int c = 0;
        for (T r : results) if (!r.created()) c++;
        return c;
    }
}
