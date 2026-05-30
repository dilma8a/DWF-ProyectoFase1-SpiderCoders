package com.spidercoders.dwf.dao;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import com.spidercoders.dwf.pojos.Empleado;
import com.spidercoders.dwf.pojos.Usuario;
import com.spidercoders.dwf.utilidades.JPAUtil;

public class EmpleadoDao {

    public Long contarActivos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(e) FROM Empleado e WHERE e.estado = 'Activo'", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public Empleado buscarPorUsuario(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT e FROM Empleado e WHERE e.usuario = :usuario", Empleado.class)
                    .setParameter("usuario", usuario)
                    .getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public Empleado buscarPorId(Integer idEmpleado) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Empleado.class, idEmpleado);
        } finally {
            em.close();
        }
    }

    public List<Empleado> listarGerentesDisponibles() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT e FROM Empleado e JOIN FETCH e.sucursal s " +
                    "WHERE e.cargo = :cargo AND e.estado IN :estados ORDER BY e.nombres, e.apellidos",
                    Empleado.class)
                    .setParameter("cargo", "Gerente de Sucursal")
                    .setParameter("estados", Arrays.asList("Activo", "En espera"))
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void actualizar(Empleado empleado) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(empleado);
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