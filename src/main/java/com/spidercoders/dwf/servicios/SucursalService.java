package com.spidercoders.dwf.servicios;

import java.util.List;

import javax.persistence.EntityManager;

import com.spidercoders.dwf.dao.EmpleadoDao;
import com.spidercoders.dwf.dao.SucursalDao;
import com.spidercoders.dwf.pojos.Empleado;
import com.spidercoders.dwf.pojos.Sucursal;
import com.spidercoders.dwf.utilidades.JPAUtil;

public class SucursalService {

    private final SucursalDao sucursalDao = new SucursalDao();
    private final EmpleadoDao empleadoDao = new EmpleadoDao();

    public Long contarSucursalesActivas() {
        return sucursalDao.contarActivas();
    }

    public List<Sucursal> listarSucursales() {
        return sucursalDao.listarTodas();
    }

    public List<Empleado> listarGerentesDisponibles() {
        return empleadoDao.listarGerentesDisponibles();
    }

    public Sucursal registrarSucursal(Sucursal sucursal, Integer idGerenteSeleccionado) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(sucursal);

            if (idGerenteSeleccionado != null) {
                Empleado gerente = em.find(Empleado.class, idGerenteSeleccionado);
                if (gerente != null) {
                    gerente.setSucursal(sucursal);
                    gerente.setEstado("Activo");
                    em.merge(gerente);
                }
            }

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