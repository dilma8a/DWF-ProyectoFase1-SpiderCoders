package com.spidercoders.dwf.servicios;

import com.spidercoders.dwf.pojos.Persona;

import java.util.List;

public interface PersonaService {

    List<Persona> listarPersonas();

    void guardarPersona(Persona persona);
}

