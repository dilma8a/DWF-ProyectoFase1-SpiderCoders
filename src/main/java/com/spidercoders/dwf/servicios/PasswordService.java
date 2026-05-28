package com.spidercoders.dwf.servicios;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordService {

    public String encriptar(String clavePlano) {
        return BCrypt.hashpw(clavePlano, BCrypt.gensalt(12));
    }

    public boolean verificar(String clavePlano, String claveHash) {
        if (clavePlano == null || claveHash == null) {
            return false;
        }

        if (claveHash.startsWith("$2a$") || claveHash.startsWith("$2b$") || claveHash.startsWith("$2y$")) {
            return BCrypt.checkpw(clavePlano, claveHash);
        }

        // Compatibilidad temporal con claves legadas en texto plano o SHA-256.
        return clavePlano.equals(claveHash) || sha256(clavePlano).equalsIgnoreCase(claveHash);
    }

    private String sha256(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encoded = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : encoded) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No se pudo calcular SHA-256", e);
        }
    }
}
