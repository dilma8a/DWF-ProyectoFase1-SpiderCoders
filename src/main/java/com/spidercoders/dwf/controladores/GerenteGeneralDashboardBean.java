package com.spidercoders.dwf.controladores;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.spidercoders.dwf.dao.ClienteDao;
import com.spidercoders.dwf.pojos.AccionPersonal;
import com.spidercoders.dwf.pojos.Movimiento;
import com.spidercoders.dwf.servicios.AccionPersonalService;
import com.spidercoders.dwf.servicios.EmpleadoService;
import com.spidercoders.dwf.servicios.MovimientoService;
import com.spidercoders.dwf.servicios.SucursalService;
import com.spidercoders.dwf.servicios.impl.EmpleadoServiceImpl;

@ManagedBean(name = "gerenteGeneralDashboardBean")
@ViewScoped
public class GerenteGeneralDashboardBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long totalSucursalesActivas;
    private Long totalMovimientos;
    private Long totalAccionesPendientes;
    private Long totalEmpleadosActivos;
    private Long totalClientes;
    private List<Movimiento> movimientosRecientes;
    private List<AccionPersonal> accionesPendientes;

    private final SucursalService sucursalService = new SucursalService();
    private final MovimientoService movimientoService = new MovimientoService();
    private final AccionPersonalService accionPersonalService = new AccionPersonalService();
    private final EmpleadoService empleadoService = new EmpleadoServiceImpl();
    private final ClienteDao clienteDao = new ClienteDao();

    @PostConstruct
    public void init() {
        recargarResumen();
    }

    public void recargarResumen() {
        totalSucursalesActivas = sucursalService.contarSucursalesActivas();
        totalMovimientos = movimientoService.contarMovimientos();
        totalAccionesPendientes = accionPersonalService.contarPendientes();
        totalEmpleadosActivos = empleadoService.contarEmpleadosActivos();
        totalClientes = clienteDao.contarActivos();
        movimientosRecientes = movimientoService.listarRecientes(5);
        accionesPendientes = accionPersonalService.listarPendientes();
    }

    public Long getTotalSucursalesActivas() {
        return totalSucursalesActivas;
    }

    public Long getTotalMovimientos() {
        return totalMovimientos;
    }

    public Long getTotalAccionesPendientes() {
        return totalAccionesPendientes;
    }

    public Long getTotalEmpleadosActivos() {
        return totalEmpleadosActivos;
    }

    public Long getTotalClientes() {
        return totalClientes;
    }

    public List<Movimiento> getMovimientosRecientes() {
        return movimientosRecientes;
    }

    public List<AccionPersonal> getAccionesPendientes() {
        return accionesPendientes;
    }
}