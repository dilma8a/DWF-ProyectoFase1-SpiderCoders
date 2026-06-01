package com.spidercoders.dwf.controladores;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.spidercoders.dwf.pojos.AccionPersonal;
import com.spidercoders.dwf.pojos.Usuario;
import com.spidercoders.dwf.servicios.AccionPersonalService;

@ManagedBean(name = "accionPersonalBean")
@ViewScoped
public class AccionPersonalBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<AccionPersonal> accionesPendientes;
    private Long totalPendientes;

    private final AccionPersonalService accionPersonalService = new AccionPersonalService();

    @PostConstruct
    public void init() {
        cargarAcciones();
    }

    public void cargarAcciones() {
        accionesPendientes = accionPersonalService.listarTodas();
        totalPendientes = accionPersonalService.contarPendientes();
    }

    public boolean esPendiente(AccionPersonal accionPersonal) {
        return accionPersonal != null && "En espera".equalsIgnoreCase(accionPersonal.getEstado());
    }

    public String aprobar(AccionPersonal accionPersonal) {
        accionPersonalService.aprobar(accionPersonal, usuarioSesion());
        cargarAcciones();
        mensaje("Accion aprobada", "La solicitud fue aprobada correctamente.");
        return null;
    }

    public String rechazar(AccionPersonal accionPersonal) {
        accionPersonalService.rechazar(accionPersonal, usuarioSesion());
        cargarAcciones();
        mensaje("Accion rechazada", "La solicitud fue rechazada correctamente.");
        return null;
    }

    private Usuario usuarioSesion() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Object usuario = externalContext.getSessionMap().get("usuarioSesion");
        return usuario instanceof Usuario ? (Usuario) usuario : null;
    }

    private void mensaje(String resumen, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, resumen, detalle));
    }

    public List<AccionPersonal> getAccionesPendientes() {
        return accionesPendientes;
    }

    public Long getTotalPendientes() {
        return totalPendientes;
    }
}