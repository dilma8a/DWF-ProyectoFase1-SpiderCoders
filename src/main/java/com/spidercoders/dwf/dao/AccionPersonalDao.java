package com.spidercoders.dwf.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.spidercoders.dwf.pojos.AccionPersonal;
import com.spidercoders.dwf.utilidades.JPAUtil;

public class AccionPersonalDao {

    public Long contarPendientes() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(a) FROM AccionPersonal a WHERE a.estado = 'En espera'", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<AccionPersonal> listarPendientes() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT a FROM AccionPersonal a " +
                    "JOIN FETCH a.empleado empleado " +
                    "JOIN FETCH a.gerenteSucursal gerenteSucursal " +
                    "LEFT JOIN FETCH a.gerenteGeneral gerenteGeneral " +
                    "WHERE a.estado = 'En espera' " +
                    "ORDER BY a.fechaSolicitud DESC",
                    AccionPersonal.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<AccionPersonal> listarTodas() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT a FROM AccionPersonal a " +
                    "JOIN FETCH a.empleado empleado " +
                    "JOIN FETCH a.gerenteSucursal gerenteSucursal " +
                    "LEFT JOIN FETCH a.gerenteGeneral gerenteGeneral " +
                    "ORDER BY a.fechaSolicitud DESC",
                    AccionPersonal.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public AccionPersonal buscarPorId(Integer idAccion) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(AccionPersonal.class, idAccion);
        } finally {
            em.close();
        }
    }

    public void actualizar(AccionPersonal accionPersonal) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(accionPersonal);
            em.getTransaction().commit();
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