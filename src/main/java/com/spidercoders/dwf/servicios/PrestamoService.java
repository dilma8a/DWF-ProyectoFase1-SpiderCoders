package com.spidercoders.dwf.servicios;

import com.spidercoders.dwf.pojos.Prestamo;

import java.util.List;

public interface PrestamoService {

    List<Prestamo> findAll();

    List<Prestamo> findByCajero(String usernameCajero);

    List<Prestamo> findEnEspera();

    Prestamo findById(Integer id);

    void save(Prestamo prestamo);

    void update(Prestamo prestamo);
}
