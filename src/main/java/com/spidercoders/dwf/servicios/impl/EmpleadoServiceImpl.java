package com.spidercoders.dwf.servicios.impl;

import com.spidercoders.dwf.dao.EmpleadoDao;
import com.spidercoders.dwf.pojos.Empleado;
import com.spidercoders.dwf.pojos.Usuario;
import com.spidercoders.dwf.servicios.EmpleadoService;

import java.util.List;

public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoDao empleadoDao;

    public EmpleadoServiceImpl() {
        this.empleadoDao = new EmpleadoDao();
    }

    @Override
    public List<Empleado> findAll() {
        return empleadoDao.findAll();
    }

    @Override
    public List<Empleado> findByGerente(String usernameGerente) {
        return empleadoDao.findByGerente(usernameGerente);
    }

    @Override
    public Empleado findById(Integer id) {
        return empleadoDao.findById(id);
    }

    @Override
    public void save(Empleado empleado) {
        empleadoDao.save(empleado);
    }

    @Override
    public void update(Empleado empleado) {
        empleadoDao.update(empleado);
    }
    
    @Override
    public Long contarEmpleadosActivos() {
        return empleadoDao.contarActivos();
    }

    @Override
    public List<Empleado> listarGerentesDisponibles() {
        return empleadoDao.listarGerentesDisponibles();
    }

    @Override
    public Empleado buscarPorUsuario(Usuario usuario) {
        return empleadoDao.buscarPorUsuario(usuario);
    }

    @Override
    public Empleado buscarPorId(Integer idEmpleado) {
        return empleadoDao.buscarPorId(idEmpleado);
    }

    @Override
    public void actualizar(Empleado empleado) {
        empleadoDao.actualizar(empleado);
    }
}
