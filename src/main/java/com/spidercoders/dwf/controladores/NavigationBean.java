package com.spidercoders.dwf.controladores;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean(name = "navigationBean")
@RequestScoped
public class NavigationBean {

    public String requireLogin() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        if (externalContext.getSessionMap().get("username") == null) {
            return "/login.xhtml?faces-redirect=true";
        }
        return null;
    }

    public String portal() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Object roleValue = externalContext.getSessionMap().get("role");
        if (roleValue == null) {
            return "/login.xhtml?faces-redirect=true";
        }

        switch (String.valueOf(roleValue)) {
            case "GERENTE_GENERAL":
                return "/views/secure/gerente-general/inicio.xhtml?faces-redirect=true";
            case "GERENTE_SUCURSAL":
                return "/views/secure/gerente-sucursal/inicio.xhtml?faces-redirect=true";
            case "DEPENDIENTE":
                return "/views/secure/dependiente/inicio.xhtml?faces-redirect=true";
            case "CAJERO":
                return "/views/secure/cajero/inicio.xhtml?faces-redirect=true";
            case "CLIENTE":
                return "/views/secure/cliente/inicio.xhtml?faces-redirect=true";
            default:
                return "/acceso-denegado.xhtml?faces-redirect=true";
        }
    }

    public String logout() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        return "/login.xhtml?faces-redirect=true&logout=true";
    }

    public String gerenteGeneral() {
        return rolePage("GERENTE_GENERAL", "/views/secure/gerente-general/inicio.xhtml");
    }

    public String gerenteSucursal() {
        return rolePage("GERENTE_SUCURSAL", "/views/secure/gerente-sucursal/inicio.xhtml");
    }

    public String dependiente() {
        return rolePage("DEPENDIENTE", "/views/secure/dependiente/inicio.xhtml");
    }

    public String cajero() {
        return rolePage("CAJERO", "/views/secure/cajero/inicio.xhtml");
    }

    public String cliente() {
        return rolePage("CLIENTE", "/views/secure/cliente/inicio.xhtml");
    }

    private String rolePage(String expectedRole, String outcome) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Object roleValue = externalContext.getSessionMap().get("role");
        if (roleValue == null) {
            return "/login.xhtml?faces-redirect=true";
        }
        if (!expectedRole.equals(String.valueOf(roleValue))) {
            return "/acceso-denegado.xhtml?faces-redirect=true";
        }
        return outcome + "?faces-redirect=true";
    }
}