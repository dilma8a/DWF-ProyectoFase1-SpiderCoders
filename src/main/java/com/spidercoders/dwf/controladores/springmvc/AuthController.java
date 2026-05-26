package com.spidercoders.dwf.controladores.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class AuthController {

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

    @GetMapping("/home")
    public String home(HttpSession session) {
        if (session == null || session.getAttribute("username") == null) {
            return "redirect:/login";
        }
        return "redirect:/home.xhtml";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "redirect:/login.xhtml";
    }

    @PostMapping("/login")
    public String login(
        @RequestParam(value = "username", required = false) String username,
        @RequestParam(value = "password", required = false) String password,
        HttpSession session
    ) {
        if (username == null || password == null || !password.equals(USERS.get(username))) {
            return "redirect:/login?error=true";
        }

        session.setAttribute("username", username);
        session.setAttribute("role", ROLES.get(username));
        return redirectToRoleHome(ROLES.get(username));
    }

    @GetMapping("/portal")
    public String portal(HttpSession session) {
        if (session == null || session.getAttribute("role") == null) {
            return "redirect:/login";
        }
        return redirectToRoleHome(String.valueOf(session.getAttribute("role")));
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login?logout=true";
    }

    @GetMapping("/gerente-general/inicio")
    public String gerenteGeneral(HttpSession session) {
        return redirectRolePage(session, "GERENTE_GENERAL", "/views/secure/gerente-general/inicio.xhtml");
    }

    @GetMapping("/gerente-sucursal/inicio")
    public String gerenteSucursal(HttpSession session) {
        return redirectRolePage(session, "GERENTE_SUCURSAL", "/views/secure/gerente-sucursal/inicio.xhtml");
    }

    @GetMapping("/dependiente/inicio")
    public String dependiente(HttpSession session) {
        return redirectRolePage(session, "DEPENDIENTE", "/views/secure/dependiente/inicio.xhtml");
    }

    @GetMapping("/cajero/inicio")
    public String cajero(HttpSession session) {
        return redirectRolePage(session, "CAJERO", "/views/secure/cajero/inicio.xhtml");
    }

    @GetMapping("/cliente/inicio")
    public String cliente(HttpSession session) {
        return redirectRolePage(session, "CLIENTE", "/views/secure/cliente/inicio.xhtml");
    }

    private String redirectRolePage(HttpSession session, String requiredRole, String xhtmlPath) {
        if (session == null || !requiredRole.equals(session.getAttribute("role"))) {
            return "redirect:/acceso-denegado.xhtml";
        }
        return "redirect:" + xhtmlPath;
    }

    private String redirectToRoleHome(String role) {
        if (role == null) {
            return "redirect:/acceso-denegado.xhtml";
        }

        switch (role) {
            case "GERENTE_GENERAL":
                return "redirect:/gerente-general/inicio";
            case "GERENTE_SUCURSAL":
                return "redirect:/gerente-sucursal/inicio";
            case "DEPENDIENTE":
                return "redirect:/dependiente/inicio";
            case "CAJERO":
                return "redirect:/cajero/inicio";
            case "CLIENTE":
                return "redirect:/cliente/inicio";
            default:
                return "redirect:/acceso-denegado.xhtml";
        }
    }
}
