package com.spidercoders.dwf.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.spidercoders.dwf.pojos.Cliente;
import com.spidercoders.dwf.pojos.Usuario;
import com.spidercoders.dwf.utilidades.JPAUtil;

public class ClienteDao {

    public Long contarActivos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(c) FROM Cliente c WHERE c.estado = 1", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public Cliente buscarPorDui(String dui) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Cliente c WHERE c.dui = :dui", Cliente.class)
                    .setParameter("dui", dui)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public Cliente buscarPorUsuario(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Cliente c WHERE c.usuario = :usuario", Cliente.class)
                    .setParameter("usuario", usuario)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void guardarClienteConUsuario(Cliente cliente, Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            cliente.setUsuario(usuario);
            em.persist(cliente);
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
