package com.spidercoders.dwf.dao;

import com.spidercoders.dwf.pojos.ClienteInfo;
import com.spidercoders.dwf.utilidades.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class ClienteInfoDao {

    public List<ClienteInfo> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM ClienteInfo c", ClienteInfo.class).getResultList();
        } finally {
            em.close();
        }
    }

    public ClienteInfo findByDui(String dui) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<ClienteInfo> result = em.createQuery(
                    "SELECT c FROM ClienteInfo c WHERE c.dui = :dui", ClienteInfo.class)
                    .setParameter("dui", dui)
                    .getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    public ClienteInfo findById(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(ClienteInfo.class, id);
        } finally {
            em.close();
        }
    }

    public void save(ClienteInfo cliente) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(cliente);
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

    public void update(ClienteInfo cliente) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(cliente);
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
