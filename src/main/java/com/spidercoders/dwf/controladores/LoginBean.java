package com.spidercoders.dwf.controladores;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.spidercoders.dwf.pojos.Usuario;
import com.spidercoders.dwf.servicios.AuthService;

@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean {

    private String username;
    private String password;

    private final AuthService authService = new AuthService();

    public void login() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        Usuario usuario = authService.autenticar(username, password);
        if (usuario == null) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credenciales incorrectas", "Verifica tu usuario, correo o contrasena."));
            return;
        }

        String role = normalizeRole(usuario.getRol() == null ? null : usuario.getRol().getNombre());
        if (role == null) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Rol no configurado", "El usuario no tiene un rol valido."));
            return;
        }

        externalContext.getSessionMap().put("username", usuario.getNombre());
        externalContext.getSessionMap().put("role", role);
        externalContext.getSessionMap().put("usuarioSesion", usuario);
        externalContext.redirect(externalContext.getRequestContextPath() + resolveRolePath(role));
        facesContext.responseComplete();
    }

    private String normalizeRole(String roleName) {
        if (roleName == null) {
            return null;
        }

        switch (roleName.trim()) {
            case "Gerente General":
                return "GERENTE_GENERAL";
            case "Gerente de Sucursal":
                return "GERENTE_SUCURSAL";
            case "Dependiente":
                return "DEPENDIENTE";
            case "Cajero":
                return "CAJERO";
            case "Cliente":
                return "CLIENTE";
            default:
                return null;
        }
    }

    private String resolveRolePath(String role) {
        if (role == null) {
            return "/acceso-denegado.xhtml";
        }

        switch (role) {
            case "GERENTE_GENERAL":
                return "/views/secure/gerente-general/dashboard.xhtml";
            case "GERENTE_SUCURSAL":
                return "/views/secure/gerente-sucursal/inicio.xhtml";
            case "DEPENDIENTE":
                return "/views/secure/dependiente/inicio.xhtml";
            case "CAJERO":
                return "/views/secure/cajero/inicio.xhtml";
            case "CLIENTE":
                return "/views/secure/cliente/inicio.xhtml";
            default:
                return "/acceso-denegado.xhtml";
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}