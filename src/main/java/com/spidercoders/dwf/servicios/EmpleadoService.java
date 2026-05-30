package com.spidercoders.dwf.servicios;

import java.util.List;

import com.spidercoders.dwf.dao.EmpleadoDao;
import com.spidercoders.dwf.pojos.Empleado;
import com.spidercoders.dwf.pojos.Usuario;

public class EmpleadoService {

    private final EmpleadoDao empleadoDao = new EmpleadoDao();

    public Long contarEmpleadosActivos() {
        return empleadoDao.contarActivos();
    }

    public List<Empleado> listarGerentesDisponibles() {
        return empleadoDao.listarGerentesDisponibles();
    }

    public Empleado buscarPorUsuario(Usuario usuario) {
        return empleadoDao.buscarPorUsuario(usuario);
    }

    public Empleado buscarPorId(Integer idEmpleado) {
        return empleadoDao.buscarPorId(idEmpleado);
    }

    public void actualizar(Empleado empleado) {
        empleadoDao.actualizar(empleado);
    }
}