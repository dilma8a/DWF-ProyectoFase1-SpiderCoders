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


CREATE TABLE permisos (
    id_permiso INT AUTO_INCREMENT PRIMARY KEY,
    nombre_permiso VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(200)
);

CREATE TABLE rol_permiso (
    id_rol_permiso INT AUTO_INCREMENT PRIMARY KEY,
    id_rol INT NOT NULL,
    id_permiso INT NOT NULL,

    FOREIGN KEY (id_rol) REFERENCES roles(id_rol),
    FOREIGN KEY (id_permiso) REFERENCES permisos(id_permiso)
);

INSERT INTO permisos (nombre_permiso, descripcion) VALUES
('VER_CLIENTES', 'Permite visualizar clientes'),
('CREAR_CLIENTES', 'Permite registrar clientes'),
('EDITAR_CLIENTES', 'Permite modificar clientes'),
('VER_PAGOS', 'Permite visualizar pagos'),
('REGISTRAR_PAGOS', 'Permite registrar pagos'),
('ANULAR_PAGOS', 'Permite anular pagos'),
('VER_EMPLEADOS', 'Permite visualizar empleados'),
('CREAR_ACCION_PERSONAL', 'Permite crear acciones de personal'),
('APROBAR_ACCION_PERSONAL', 'Permite aprobar o rechazar acciones de personal'),
('VER_REPORTES', 'Permite visualizar reportes'),
('ADMINISTRAR_SUCURSALES', 'Permite administrar sucursales'),
('ADMINISTRAR_USUARIOS', 'Permite administrar usuarios');

-- Cliente
INSERT INTO rol_permiso (id_rol, id_permiso) VALUES
(1, 1),
(1, 4);

-- Dependiente
INSERT INTO rol_permiso (id_rol, id_permiso) VALUES
(2, 1),
(2, 2),
(2, 3);

-- Cajero
INSERT INTO rol_permiso (id_rol, id_permiso) VALUES
(3, 1),
(3, 4),
(3, 5),
(3, 6);

-- Gerente de Sucursal
INSERT INTO rol_permiso (id_rol, id_permiso) VALUES
(4, 1),
(4, 4),
(4, 7),
(4, 8),
(4, 10);

-- Gerente General
INSERT INTO rol_permiso (id_rol, id_permiso)
SELECT 5, id_permiso FROM permisos;
