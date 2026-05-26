package com.spidercoders.dwf.servicios.impl;

import com.spidercoders.dwf.dao.PersonaDao;
import com.spidercoders.dwf.pojos.Persona;
import com.spidercoders.dwf.servicios.PersonaService;

import java.util.List;

public class PersonaServiceImpl implements PersonaService {

    private final PersonaDao personaDao;

    public PersonaServiceImpl() {
        this.personaDao = new PersonaDao();
    }

    @Override
    public List<Persona> listarPersonas() {
        return personaDao.findAll();
    }

    @Override
    public void guardarPersona(Persona persona) {
        personaDao.save(persona);
    }
}

