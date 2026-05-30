package com.spidercoders.dwf.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.spidercoders.dwf.pojos.Movimiento;
import com.spidercoders.dwf.utilidades.JPAUtil;

public class MovimientoDao {

    public Long contarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(m) FROM Movimiento m", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<Movimiento> listarRecientes(int maxResults) {
        return buscarFiltrados(null, null, null, null, maxResults);
    }

    public List<Movimiento> buscarFiltrados(Integer idSucursal, String tipoMovimiento, LocalDateTime desde, LocalDateTime hasta, int maxResults) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT DISTINCT m FROM Movimiento m ")
                    .append("JOIN FETCH m.cuenta cuenta ")
                    .append("JOIN FETCH cuenta.cliente cliente ")
                    .append("JOIN FETCH cuenta.sucursal sucursal ")
                    .append("JOIN FETCH m.usuario usuario ")
                    .append("WHERE 1 = 1 ");

            if (idSucursal != null) {
                jpql.append("AND sucursal.idSucursal = :idSucursal ");
            }
            if (tipoMovimiento != null && !tipoMovimiento.trim().isEmpty()) {
                jpql.append("AND m.tipoMovimiento = :tipoMovimiento ");
            }
            if (desde != null) {
                jpql.append("AND m.fechaMovimiento >= :desde ");
            }
            if (hasta != null) {
                jpql.append("AND m.fechaMovimiento <= :hasta ");
            }
            jpql.append("ORDER BY m.fechaMovimiento DESC");

            TypedQuery<Movimiento> query = em.createQuery(jpql.toString(), Movimiento.class);
            if (idSucursal != null) {
                query.setParameter("idSucursal", idSucursal);
            }
            if (tipoMovimiento != null && !tipoMovimiento.trim().isEmpty()) {
                query.setParameter("tipoMovimiento", tipoMovimiento.trim());
            }
            if (desde != null) {
                query.setParameter("desde", desde);
            }
            if (hasta != null) {
                query.setParameter("hasta", hasta);
            }
            if (maxResults > 0) {
                query.setMaxResults(maxResults);
            }
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}