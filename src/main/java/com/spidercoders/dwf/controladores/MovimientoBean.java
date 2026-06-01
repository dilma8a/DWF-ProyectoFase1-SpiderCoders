package com.spidercoders.dwf.controladores;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.spidercoders.dwf.pojos.Movimiento;
import com.spidercoders.dwf.pojos.Sucursal;
import com.spidercoders.dwf.servicios.MovimientoService;
import com.spidercoders.dwf.servicios.SucursalService;

@ManagedBean(name = "movimientoBean")
@ViewScoped
public class MovimientoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idSucursalFiltro;
    private String tipoMovimientoFiltro;
    private String fechaDesde;
    private String fechaHasta;
    private List<String> tiposMovimiento;
    private List<Sucursal> sucursales;
    private List<Movimiento> movimientos;

    private final MovimientoService movimientoService = new MovimientoService();
    private final SucursalService sucursalService = new SucursalService();

    @PostConstruct
    public void init() {
        tiposMovimiento = Arrays.asList("Deposito", "Retiro", "Transferencia enviada", "Transferencia recibida");
        sucursales = sucursalService.listarSucursales();
        movimientos = movimientoService.listarRecientes(20);
    }

    public String filtrar() {
        movimientos = movimientoService.filtrarMovimientos(idSucursalFiltro, tipoMovimientoFiltro, fechaDesde, fechaHasta);
        return null;
    }

    public String limpiarFiltros() {
        idSucursalFiltro = null;
        tipoMovimientoFiltro = null;
        fechaDesde = null;
        fechaHasta = null;
        movimientos = movimientoService.listarRecientes(20);
        return null;
    }

    public Integer getIdSucursalFiltro() {
        return idSucursalFiltro;
    }

    public void setIdSucursalFiltro(Integer idSucursalFiltro) {
        this.idSucursalFiltro = idSucursalFiltro;
    }

    public String getTipoMovimientoFiltro() {
        return tipoMovimientoFiltro;
    }

    public void setTipoMovimientoFiltro(String tipoMovimientoFiltro) {
        this.tipoMovimientoFiltro = tipoMovimientoFiltro;
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public List<String> getTiposMovimiento() {
        return tiposMovimiento;
    }

    public List<Sucursal> getSucursales() {
        return sucursales;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }
}