package com.spidercoders.dwf.servicios;

import com.spidercoders.dwf.dao.ClienteDao;
import com.spidercoders.dwf.dao.UsuarioDao;
import com.spidercoders.dwf.pojos.Cliente;
import com.spidercoders.dwf.pojos.Usuario;

public class ResetClaveService {

    private final UsuarioDao usuarioDao = new UsuarioDao();
    private final ClienteDao clienteDao = new ClienteDao();
    private final PasswordService passwordService = new PasswordService();

    public String restablecer(String correo, String usuarioIngresado, String nuevaClave, String confirmarClave) {
        String validacion = validarFormulario(correo, usuarioIngresado, nuevaClave, confirmarClave);
        if (validacion != null) {
            return validacion;
        }

        Usuario usuario = usuarioDao.buscarPorCorreo(correo.trim().toLowerCase());
        if (usuario == null) {
            return "No existe un usuario registrado con ese correo.";
        }

        if (!usuario.getNombre().equalsIgnoreCase(usuarioIngresado.trim())) {
            return "El usuario no coincide con el correo ingresado.";
        }

        usuario.setClave(passwordService.encriptar(nuevaClave));
        usuarioDao.actualizar(usuario);
        return null;
    }

    private String validarFormulario(String correo, String usuarioIngresado, String nuevaClave, String confirmarClave) {
        if (correo == null || correo.trim().isEmpty()) {
            return "Ingrese su correo.";
        }
        if (usuarioIngresado == null || usuarioIngresado.trim().isEmpty()) {
            return "Ingrese su usuario.";
        }
        if (nuevaClave == null || nuevaClave.length() < 6) {
            return "La nueva contrasena debe tener al menos 6 caracteres.";
        }
        if (!nuevaClave.equals(confirmarClave)) {
            return "Las contrasenas no coinciden.";
        }
        return null;
    }
}
