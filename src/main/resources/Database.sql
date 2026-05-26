CREATE DATABASE IF NOT EXISTS proyectodwf
CHARACTER SET utf8mb4
COLLATE utf8mb4_spanish_ci;

USE proyectodwf;

CREATE TABLE roles (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(60) NOT NULL UNIQUE,
    descripcion VARCHAR(200),
    estado TINYINT DEFAULT 1
);

CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    id_rol INT NOT NULL,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    correo VARCHAR(100) NOT NULL UNIQUE,
    clave VARCHAR(255) NOT NULL,
    estado TINYINT DEFAULT 1,
    ultimo_acceso DATETIME NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
);


INSERT INTO roles (nombre, descripcion) VALUES
('Cliente', 'Usuario que consulta información y realiza operaciones como cliente'),
('Dependiente', 'Usuario que atiende operaciones básicas'),
('Cajero', 'Usuario que registra pagos o cobros'),
('Gerente de Sucursal', 'Usuario que administra una sucursal'),
('Gerente General', 'Usuario con acceso general al sistema');

