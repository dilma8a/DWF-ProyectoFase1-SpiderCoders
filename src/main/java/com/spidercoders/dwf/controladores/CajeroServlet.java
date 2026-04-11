package com.spidercoders.dwf.controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class CajeroServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"CAJERO".equals(session.getAttribute("role"))) {
            resp.sendRedirect(req.getContextPath() + "/acceso-denegado.xhtml");
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/views/secure/cajero/inicio.xhtml");
    }
}
