package com.spidercoders.dwf.servicios;

import com.spidercoders.dwf.pojos.Empleado;
import com.spidercoders.dwf.pojos.Usuario;

import java.util.List;

public interface EmpleadoService {

    List<Empleado> findAll();

    List<Empleado> findByGerente(String usernameGerente);

    Empleado findById(Integer id);

    void save(Empleado empleado);

    void update(Empleado empleado);
    
    Long contarEmpleadosActivos();

    List<Empleado> listarGerentesDisponibles();

    Empleado buscarPorUsuario(Usuario usuario);

    Empleado buscarPorId(Integer idEmpleado);

    void actualizar(Empleado empleado);
}