package com.spidercoders.dwf.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.spidercoders.dwf.pojos.Sucursal;
import com.spidercoders.dwf.utilidades.JPAUtil;

public class SucursalDao {

    public Long contarActivas() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(s) FROM Sucursal s WHERE s.estado = 1", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<Sucursal> listarTodas() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT s FROM Sucursal s ORDER BY s.nombre", Sucursal.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Sucursal buscarPorId(Integer idSucursal) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Sucursal.class, idSucursal);
        } finally {
            em.close();
        }
    }

    public Sucursal guardar(Sucursal sucursal) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(sucursal);
            em.getTransaction().commit();
            return sucursal;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}