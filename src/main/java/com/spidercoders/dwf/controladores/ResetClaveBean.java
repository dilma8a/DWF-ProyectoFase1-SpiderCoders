package com.spidercoders.dwf.controladores;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.spidercoders.dwf.servicios.ResetClaveService;

@ManagedBean(name = "resetClaveBean")
@RequestScoped
public class ResetClaveBean {

    private String correo;
    private String usuario;
    private String nuevaClave;
    private String confirmarClave;

    private final ResetClaveService resetClaveService = new ResetClaveService();

    public String restablecerClave() {
        String error = resetClaveService.restablecer(correo, usuario, nuevaClave, confirmarClave);
        if (error != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
            return null;
        }

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Contrasena actualizada",
                        "Ahora puedes iniciar sesion con tu nueva contrasena."));

        return "/login.xhtml?faces-redirect=true";
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNuevaClave() {
        return nuevaClave;
    }

    public void setNuevaClave(String nuevaClave) {
        this.nuevaClave = nuevaClave;
    }

    public String getConfirmarClave() {
        return confirmarClave;
    }

    public void setConfirmarClave(String confirmarClave) {
        this.confirmarClave = confirmarClave;
    }
}
