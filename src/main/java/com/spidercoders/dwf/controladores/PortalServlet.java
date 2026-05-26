package com.spidercoders.dwf.controladores;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

public class PortalServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("role") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String role = String.valueOf(session.getAttribute("role"));
        switch (role) {
            case "GERENTE_GENERAL":
                resp.sendRedirect(req.getContextPath() + "/gerente-general/inicio");
                break;
            case "GERENTE_SUCURSAL":
                resp.sendRedirect(req.getContextPath() + "/gerente-sucursal/inicio");
                break;
            case "DEPENDIENTE":
                resp.sendRedirect(req.getContextPath() + "/dependiente/inicio");
                break;
            case "CAJERO":
                resp.sendRedirect(req.getContextPath() + "/cajero/inicio");
                break;
            case "CLIENTE":
                resp.sendRedirect(req.getContextPath() + "/cliente/inicio");
                break;
            default:
                resp.sendRedirect(req.getContextPath() + "/acceso-denegado.xhtml");
                break;
        }
    }
}

