package com.spidercoders.dwf.controladores;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class RolePageFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("role") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String role = String.valueOf(session.getAttribute("role"));
        String uri = req.getRequestURI();

        boolean authorized =
            (uri.contains("/views/secure/gerente-general/") && "GERENTE_GENERAL".equals(role)) ||
            (uri.contains("/views/secure/gerente-sucursal/") && "GERENTE_SUCURSAL".equals(role)) ||
            (uri.contains("/views/secure/dependiente/") && "DEPENDIENTE".equals(role)) ||
            (uri.contains("/views/secure/cajero/") && "CAJERO".equals(role)) ||
            (uri.contains("/views/secure/cliente/") && "CLIENTE".equals(role));

        if (!authorized) {
            resp.sendRedirect(req.getContextPath() + "/acceso-denegado.xhtml");
            return;
        }

        chain.doFilter(request, response);
    }
}
