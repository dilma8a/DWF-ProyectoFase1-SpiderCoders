package com.spidercoders.dwf.dao;

import com.spidercoders.dwf.pojos.Prestamo;
import com.spidercoders.dwf.utilidades.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class PrestamoDao {

    public List<Prestamo> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Prestamo p", Prestamo.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Prestamo> findByCajero(String usernameCajero) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT p FROM Prestamo p WHERE p.usernameCajero = :cajero ORDER BY p.fechaSolicitud DESC",
                    Prestamo.class)
                    .setParameter("cajero", usernameCajero)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Prestamo> findEnEspera() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT p FROM Prestamo p WHERE p.estado = 'EN_ESPERA' ORDER BY p.fechaSolicitud DESC",
                    Prestamo.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Prestamo findById(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Prestamo.class, id);
        } finally {
            em.close();
        }
    }

    public void save(Prestamo prestamo) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(prestamo);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    public void update(Prestamo prestamo) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(prestamo);
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }
}
