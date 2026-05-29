package com.spidercoders.dwf.utilidades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public final class JPAUtil {

    private static final EntityManagerFactory EMF;

    static {
        Map<String, Object> props = new HashMap<>();

        String dbHost = getEnv("DB_HOST", "localhost");
        String dbPort = getEnv("DB_PORT", "3306");
        String dbName = getEnv("DB_NAME", "dwf_fase1");
        String dbUser = getEnv("DB_USER", "root");
        String dbPass = getEnv("DB_PASS", "root");

        props.put("javax.persistence.jdbc.url",
            "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName
                + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
        props.put("javax.persistence.jdbc.user", dbUser);
        props.put("javax.persistence.jdbc.password", dbPass);

        EMF = Persistence.createEntityManagerFactory("dwfPU", props);
    }

    private JPAUtil() {
    }

    public static EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

    private static String getEnv(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value != null && !value.isEmpty()) ? value : defaultValue;
    }
}
