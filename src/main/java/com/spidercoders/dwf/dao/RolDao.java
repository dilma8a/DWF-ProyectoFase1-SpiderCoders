package com.spidercoders.dwf.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.spidercoders.dwf.pojos.Rol;
import com.spidercoders.dwf.utilidades.JPAUtil;

public class RolDao {

    public Rol buscarPorNombre(String nombre) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT r FROM Rol r WHERE r.nombre = :nombre AND r.estado = 1",
                    Rol.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
