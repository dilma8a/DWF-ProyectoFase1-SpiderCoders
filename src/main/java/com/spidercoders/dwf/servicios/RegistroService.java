package com.spidercoders.dwf.servicios;

import java.math.BigDecimal;

import com.spidercoders.dwf.dao.ClienteDao;
import com.spidercoders.dwf.dao.RolDao;
import com.spidercoders.dwf.dao.UsuarioDao;
import com.spidercoders.dwf.pojos.Cliente;
import com.spidercoders.dwf.pojos.Rol;
import com.spidercoders.dwf.pojos.Usuario;

public class RegistroService {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private final UsuarioDao usuarioDao = new UsuarioDao();
    private final ClienteDao clienteDao = new ClienteDao();
    private final RolDao rolDao = new RolDao();
    private final PasswordService passwordService = new PasswordService();

    public String registrarCliente(String nombres, String apellidos, String nombreUsuario, String correo, String dui,
                                   String telefono, String direccion, BigDecimal salario,
                                   String clave, String confirmarClave, boolean aceptaTerminos) {

        String validacion = validarFormulario(nombres, apellidos, nombreUsuario, correo, dui, salario, clave, confirmarClave, aceptaTerminos);
        if (validacion != null) {
            return validacion;
        }

        Rol rolCliente = rolDao.buscarPorNombre("Cliente");
        if (rolCliente == null) {
            return "No existe el rol Cliente en la base de datos.";
        }

        Usuario usuario = new Usuario();
        usuario.setRol(rolCliente);
        usuario.setNombre(nombreUsuario.trim());
        usuario.setCorreo(correo.trim().toLowerCase());
        usuario.setClave(passwordService.encriptar(clave));
        usuario.setEstado(1);

        Cliente cliente = new Cliente();
        cliente.setNombres(nombres.trim());
        cliente.setApellidos(apellidos.trim());
        cliente.setDui(dui.trim());
        cliente.setTelefono(telefono == null ? null : telefono.trim());
        cliente.setDireccion(direccion == null ? null : direccion.trim());
        cliente.setSalario(salario);
        cliente.setEstado(1);

        try {
            clienteDao.guardarClienteConUsuario(cliente, usuario);
            return null;
        } catch (Exception e) {
            return "No se pudo crear la cuenta. Verifique los datos ingresados.";
        }
    }

    private String validarFormulario(String nombres, String apellidos, String nombreUsuario, String correo, String dui,
                                     BigDecimal salario, String clave, String confirmarClave, boolean aceptaTerminos) {

        if (nombres == null || nombres.trim().isEmpty()) {
            return "Ingrese sus nombres.";
        }
        if (apellidos == null || apellidos.trim().isEmpty()) {
            return "Ingrese sus apellidos.";
        }
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            return "Ingrese su nombre de usuario.";
        }
        if (correo == null || correo.trim().isEmpty()) {
            return "Ingrese su correo.";
        }
        if (!correo.trim().matches(EMAIL_REGEX)) {
            return "Ingrese un correo valido.";
        }
        if (dui == null || !dui.matches("^[0-9]{8}-[0-9]{1}$")) {
            return "El DUI debe tener el formato 00000000-0.";
        }
        if (salario == null || salario.compareTo(BigDecimal.ZERO) <= 0) {
            return "Ingrese un salario valido.";
        }
        if (clave == null || clave.length() < 6) {
            return "La contrasena debe tener al menos 6 caracteres.";
        }
        if (!clave.equals(confirmarClave)) {
            return "Las contrasenas no coinciden.";
        }
        if (!aceptaTerminos) {
            return "Debe aceptar los terminos y condiciones.";
        }
        if (usuarioDao.existeCorreo(correo.trim().toLowerCase())) {
            return "El correo ya esta registrado.";
        }
        if (usuarioDao.existeNombreUsuario(nombreUsuario.trim())) {
            return "El nombre de usuario ya existe.";
        }
        if (clienteDao.buscarPorDui(dui.trim()) != null) {
            return "El DUI ya esta registrado.";
        }

        return null;
    }
}
