package com.spidercoders.dwf.dao;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.spidercoders.dwf.pojos.Usuario;
import com.spidercoders.dwf.utilidades.JPAUtil;

public class UsuarioDao {

    public Usuario buscarPorUsuarioOCorreo(String valor) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String filtro = valor == null ? "" : valor.trim();
            return em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.nombre = :valor OR LOWER(u.correo) = :correo",
                    Usuario.class)
                    .setParameter("valor", filtro)
                    .setParameter("correo", filtro.toLowerCase())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public Usuario buscarPorCorreo(String correo) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT u FROM Usuario u WHERE LOWER(u.correo) = :correo",
                    Usuario.class)
                    .setParameter("correo", correo == null ? "" : correo.trim().toLowerCase())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public boolean existeCorreo(String correo) {
        return buscarPorCorreo(correo) != null;
    }

    public boolean existeNombreUsuario(String nombre) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Long total = em.createQuery(
                    "SELECT COUNT(u) FROM Usuario u WHERE u.nombre = :nombre",
                    Long.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
            return total != null && total > 0;
        } finally {
            em.close();
        }
    }

    public void guardar(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(usuario);
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

    public void actualizar(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(usuario);
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

    public void actualizarUltimoAcceso(Usuario usuario) {
        usuario.setUltimoAcceso(LocalDateTime.now());
        actualizar(usuario);
    }
}
