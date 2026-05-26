package com.spidercoders.dwf.controladores;

import java.io.IOException;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean {

    private static final Map<String, String> USERS = Map.of(
        "gerente_general", "gerente123",
        "gerente_sucursal", "sucursal123",
        "dependiente", "dependiente123",
        "cajero", "cajero123",
        "cliente", "cliente123"
    );

    private static final Map<String, String> ROLES = Map.of(
        "gerente_general", "GERENTE_GENERAL",
        "gerente_sucursal", "GERENTE_SUCURSAL",
        "dependiente", "DEPENDIENTE",
        "cajero", "CAJERO",
        "cliente", "CLIENTE"
    );

    private String username;
    private String password;

    public void login() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        if (username == null || password == null || !password.equals(USERS.get(username))) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario o clave incorrectos.", null));
            return;
        }

        String role = ROLES.get(username);
        externalContext.getSessionMap().put("username", username);
        externalContext.getSessionMap().put("role", role);
        externalContext.redirect(externalContext.getRequestContextPath() + resolveRolePath(role));
        facesContext.responseComplete();
    }

    private String resolveRolePath(String role) {
        if (role == null) {
            return "/acceso-denegado.xhtml";
        }

        switch (role) {
            case "GERENTE_GENERAL":
                return "/views/secure/gerente-general/inicio.xhtml";
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