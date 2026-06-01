package com.spidercoders.dwf.servicios;

import java.time.LocalDateTime;
import java.util.List;

import com.spidercoders.dwf.dao.AccionPersonalDao;
import com.spidercoders.dwf.dao.EmpleadoDao;
import com.spidercoders.dwf.pojos.AccionPersonal;
import com.spidercoders.dwf.pojos.Empleado;
import com.spidercoders.dwf.pojos.Usuario;

public class AccionPersonalService {

    private final AccionPersonalDao accionPersonalDao = new AccionPersonalDao();
    private final EmpleadoDao empleadoDao = new EmpleadoDao();

    public Long contarPendientes() {
        return accionPersonalDao.contarPendientes();
    }

    public List<AccionPersonal> listarPendientes() {
        return accionPersonalDao.listarPendientes();
    }

    public List<AccionPersonal> listarTodas() {
        return accionPersonalDao.listarTodas();
    }

    public void aprobar(AccionPersonal accionPersonal, Usuario usuarioSesion) {
        resolver(accionPersonal, "Aprobada", usuarioSesion);
    }

    public void rechazar(AccionPersonal accionPersonal, Usuario usuarioSesion) {
        resolver(accionPersonal, "Rechazada", usuarioSesion);
    }

    private void resolver(AccionPersonal accionPersonal, String estado, Usuario usuarioSesion) {
        if (accionPersonal == null) {
            return;
        }

        AccionPersonal persistida = accionPersonalDao.buscarPorId(accionPersonal.getIdAccion());
        if (persistida == null) {
            return;
        }

        Empleado gerenteGeneral = empleadoDao.buscarPorUsuario(usuarioSesion);
        persistida.setEstado(estado);
        persistida.setFechaResolucion(LocalDateTime.now());
        persistida.setGerenteGeneral(gerenteGeneral);
        accionPersonalDao.actualizar(persistida);
    }
}