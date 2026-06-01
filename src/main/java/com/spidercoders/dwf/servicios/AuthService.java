package com.spidercoders.dwf.servicios;

import com.spidercoders.dwf.dao.UsuarioDao;
import com.spidercoders.dwf.pojos.Usuario;

public class AuthService {

    private final UsuarioDao usuarioDao = new UsuarioDao();
    private final PasswordService passwordService = new PasswordService();

    public Usuario autenticar(String usuarioOCorreo, String clave) {
        if (usuarioOCorreo == null || usuarioOCorreo.trim().isEmpty()) {
            return null;
        }
        if (clave == null || clave.trim().isEmpty()) {
            return null;
        }

        Usuario usuario = usuarioDao.buscarPorUsuarioOCorreo(usuarioOCorreo.trim());
        if (usuario == null) {
            return null;
        }
        if (usuario.getEstado() == null || usuario.getEstado() != 1) {
            return null;
        }
        if (!passwordService.verificar(clave, usuario.getClave())) {
            return null;
        }

        usuarioDao.actualizarUltimoAcceso(usuario);
        return usuario;
    }
}
