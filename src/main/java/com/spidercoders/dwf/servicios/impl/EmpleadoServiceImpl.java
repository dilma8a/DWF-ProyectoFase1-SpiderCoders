package com.spidercoders.dwf.servicios.impl;

import com.spidercoders.dwf.dao.EmpleadoDao;
import com.spidercoders.dwf.pojos.Empleado;
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
}
