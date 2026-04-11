package com.spidercoders.dwf.controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;

public class LoginServlet extends HttpServlet {

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/login.xhtml");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || password == null || !password.equals(USERS.get(username))) {
            resp.sendRedirect(req.getContextPath() + "/login?error=true");
            return;
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("username", username);
        String role = ROLES.get(username);
        session.setAttribute("role", role);

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
