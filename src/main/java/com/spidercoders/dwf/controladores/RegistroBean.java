package com.spidercoders.dwf.controladores;

import java.math.BigDecimal;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.spidercoders.dwf.servicios.RegistroService;

@ManagedBean(name = "registroBean")
@RequestScoped
public class RegistroBean {

    private String nombres;
    private String apellidos;
    private String usuario;
    private String correo;
    private String dui;
    private String telefono;
    private String direccion;
    private BigDecimal salario;
    private String clave;
    private String confirmarClave;
    private boolean aceptaTerminos;

    private final RegistroService registroService = new RegistroService();

    public String registrar() {
        String error = registroService.registrarCliente(
                nombres,
                apellidos,
            usuario,
                correo,
                dui,
                telefono,
                direccion,
                salario,
                clave,
                confirmarClave,
                aceptaTerminos);


        if (error != null) {
            FacesContext.getCurrentInstance().addMessage("registroForm:usuario",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
            FacesContext.getCurrentInstance().addMessage("registroForm:usuario",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese su nombre de usuario", null));
            return null;
        }

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro exitoso",
                        "Tu cuenta fue creada correctamente. Ahora puedes iniciar sesion."));

        return "/login.xhtml?faces-redirect=true";
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getConfirmarClave() {
        return confirmarClave;
    }

    public void setConfirmarClave(String confirmarClave) {
        this.confirmarClave = confirmarClave;
    }

    public boolean isAceptaTerminos() {
        return aceptaTerminos;
    }

    public void setAceptaTerminos(boolean aceptaTerminos) {
        this.aceptaTerminos = aceptaTerminos;
    }
}
