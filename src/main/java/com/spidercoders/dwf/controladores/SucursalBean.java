package com.spidercoders.dwf.controladores;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.spidercoders.dwf.pojos.Empleado;
import com.spidercoders.dwf.pojos.Sucursal;
import com.spidercoders.dwf.servicios.EmpleadoService;
import com.spidercoders.dwf.servicios.SucursalService;

@ManagedBean(name = "sucursalBean")
@ViewScoped
public class SucursalBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Sucursal sucursal;
    private List<Empleado> gerentesDisponibles;
    private List<Sucursal> sucursales;
    private Integer idGerenteSeleccionado;

    private final SucursalService sucursalService = new SucursalService();
    private final EmpleadoService empleadoService = new EmpleadoService();

    @PostConstruct
    public void init() {
        limpiarFormulario();
        cargarGerentesDisponibles();
        cargarSucursales();
    }

    public void cargarGerentesDisponibles() {
        gerentesDisponibles = empleadoService.listarGerentesDisponibles();
    }

    public void cargarSucursales() {
        sucursales = sucursalService.listarSucursales();
    }

    public String guardar() {
        try {
            sucursalService.registrarSucursal(sucursal, idGerenteSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Sucursal registrada",
                            "La nueva sucursal fue guardada correctamente."));
            limpiarFormulario();
            cargarGerentesDisponibles();
            cargarSucursales();
            return null;
        } catch (RuntimeException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "No fue posible guardar la sucursal",
                            e.getMessage()));
            return null;
        }
    }

    public void limpiarFormulario() {
        sucursal = new Sucursal();
        sucursal.setEstado(1);
        idGerenteSeleccionado = null;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public List<Empleado> getGerentesDisponibles() {
        return gerentesDisponibles;
    }

    public List<Sucursal> getSucursales() {
        return sucursales;
    }

    public Integer getIdGerenteSeleccionado() {
        return idGerenteSeleccionado;
    }

    public void setIdGerenteSeleccionado(Integer idGerenteSeleccionado) {
        this.idGerenteSeleccionado = idGerenteSeleccionado;
    }
}