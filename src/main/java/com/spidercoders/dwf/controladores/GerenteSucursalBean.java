package com.spidercoders.dwf.controladores;

import com.spidercoders.dwf.pojos.Empleado;
import com.spidercoders.dwf.pojos.Prestamo;
import com.spidercoders.dwf.servicios.impl.EmpleadoServiceImpl;
import com.spidercoders.dwf.servicios.impl.PrestamoServiceImpl;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "gerenteSucursalBean")
@SessionScoped
public class GerenteSucursalBean {

    // Dashboard metrics
    private int totalPrestamosEspera;
    private int totalEmpleadosActivos;
    private int totalPrestamosAprobados;

    // Lista de préstamos para revisar
    private List<Prestamo> prestamosEnEspera;

    // Para rechazar: observación
    private String observacionRechazo;

    // Mensaje resultado
    private String mensajeResultado;

    private final PrestamoServiceImpl prestamoService = new PrestamoServiceImpl();
    private final EmpleadoServiceImpl empleadoService = new EmpleadoServiceImpl();

    // -----------------------------------------------------------------------
    // Método cargarDashboard
    // -----------------------------------------------------------------------
    public void cargarDashboard() {
        // Cargar préstamos en espera
        prestamosEnEspera = prestamoService.findEnEspera();
        if (prestamosEnEspera == null) {
            prestamosEnEspera = new ArrayList<>();
        }
        totalPrestamosEspera = prestamosEnEspera.size();

        // Contar empleados activos
        List<Empleado> listaEmpleados = empleadoService.findAll();
        if (listaEmpleados == null) {
            listaEmpleados = new ArrayList<>();
        }
        totalEmpleadosActivos = (int) listaEmpleados.stream()
            .filter(e -> "ACTIVO".equals(e.getEstado()))
            .count();

        // Contar préstamos aprobados
        List<Prestamo> todosPrestamos = prestamoService.findAll();
        if (todosPrestamos == null) {
            todosPrestamos = new ArrayList<>();
        }
        totalPrestamosAprobados = (int) todosPrestamos.stream()
            .filter(p -> "APROBADO".equals(p.getEstado()))
            .count();
    }

    // -----------------------------------------------------------------------
    // Método aprobarPrestamo
    // -----------------------------------------------------------------------
    public void aprobarPrestamo(Integer idPrestamo) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String usernameGerente = (String) externalContext.getSessionMap().get("username");

        Prestamo p = prestamoService.findById(idPrestamo);
        if (p == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "No se encontró el préstamo con ID: " + idPrestamo));
            return;
        }

        p.setEstado("APROBADO");
        p.setFechaResolucion(new Date());
        p.setUsernameGerente(usernameGerente);

        prestamoService.update(p);

        mensajeResultado = "Préstamo #" + idPrestamo + " aprobado correctamente.";

        cargarDashboard();
    }

    // -----------------------------------------------------------------------
    // Método rechazarPrestamo
    // -----------------------------------------------------------------------
    public void rechazarPrestamo(Integer idPrestamo) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String usernameGerente = (String) externalContext.getSessionMap().get("username");

        Prestamo p = prestamoService.findById(idPrestamo);
        if (p == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "No se encontró el préstamo con ID: " + idPrestamo));
            return;
        }

        p.setEstado("RECHAZADO");
        p.setFechaResolucion(new Date());
        p.setObservacionesGerente(observacionRechazo);
        p.setUsernameGerente(usernameGerente);

        prestamoService.update(p);

        observacionRechazo = "";
        mensajeResultado = "Préstamo #" + idPrestamo + " rechazado.";

        cargarDashboard();
    }

    // -----------------------------------------------------------------------
    // Getters y Setters
    // -----------------------------------------------------------------------

    public int getTotalPrestamosEspera() {
        return totalPrestamosEspera;
    }

    public void setTotalPrestamosEspera(int totalPrestamosEspera) {
        this.totalPrestamosEspera = totalPrestamosEspera;
    }

    public int getTotalEmpleadosActivos() {
        return totalEmpleadosActivos;
    }

    public void setTotalEmpleadosActivos(int totalEmpleadosActivos) {
        this.totalEmpleadosActivos = totalEmpleadosActivos;
    }

    public int getTotalPrestamosAprobados() {
        return totalPrestamosAprobados;
    }

    public void setTotalPrestamosAprobados(int totalPrestamosAprobados) {
        this.totalPrestamosAprobados = totalPrestamosAprobados;
    }

    public List<Prestamo> getPrestamosEnEspera() {
        return prestamosEnEspera;
    }

    public void setPrestamosEnEspera(List<Prestamo> prestamosEnEspera) {
        this.prestamosEnEspera = prestamosEnEspera;
    }

    public String getObservacionRechazo() {
        return observacionRechazo;
    }

    public void setObservacionRechazo(String observacionRechazo) {
        this.observacionRechazo = observacionRechazo;
    }

    public String getMensajeResultado() {
        return mensajeResultado;
    }

    public void setMensajeResultado(String mensajeResultado) {
        this.mensajeResultado = mensajeResultado;
    }
}
