CREATE DATABASE IF NOT EXISTS dwf_fase1
CHARACTER SET utf8mb4
COLLATE utf8mb4_spanish_ci;

USE dwf_fase1;

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

-- Sucursales
CREATE TABLE IF NOT EXISTS sucursales (
    id_sucursal INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(15),
    estado TINYINT DEFAULT 1
);

-- Clientes del banco (personas que piden préstamos)
CREATE TABLE IF NOT EXISTS clientes_info (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    dui VARCHAR(10) NOT NULL UNIQUE,
    fecha_nacimiento DATE,
    telefono VARCHAR(15),
    direccion VARCHAR(255),
    salario DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    estado TINYINT DEFAULT 1,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Empleados registrados por el Gerente de Sucursal
CREATE TABLE IF NOT EXISTS empleados (
    id_empleado INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    clave VARCHAR(255) NOT NULL,
    tipo_empleado ENUM('CAJERO','LIMPIEZA','SECRETARIA','ASESOR_MESA') NOT NULL,
    salario DECIMAL(10,2),
    fecha_ingreso DATE,
    estado ENUM('ACTIVO','INACTIVO') DEFAULT 'ACTIVO',
    username_gerente VARCHAR(50) NOT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Acciones de personal generadas por Gerente de Sucursal (aprobadas por Gerente General)
CREATE TABLE IF NOT EXISTS acciones_personal (
    id_accion INT AUTO_INCREMENT PRIMARY KEY,
    id_empleado INT NOT NULL,
    username_gerente VARCHAR(50) NOT NULL,
    tipo_accion ENUM('ALTA','BAJA') NOT NULL,
    estado ENUM('EN_ESPERA','APROBADO','RECHAZADO') DEFAULT 'EN_ESPERA',
    observaciones TEXT,
    fecha_solicitud DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_resolucion DATETIME,
    FOREIGN KEY (id_empleado) REFERENCES empleados(id_empleado)
);

-- Préstamos aperturados por cajeros
CREATE TABLE IF NOT EXISTS prestamos (
    id_prestamo INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    username_cajero VARCHAR(50) NOT NULL,
    monto_solicitado DECIMAL(10,2) NOT NULL,
    tasa_interes DECIMAL(5,2) NOT NULL,
    cuota_mensual DECIMAL(10,2) NOT NULL,
    plazo_meses INT NOT NULL,
    anios_pago DECIMAL(5,2) NOT NULL,
    proposito VARCHAR(255),
    estado ENUM('EN_ESPERA','APROBADO','RECHAZADO') DEFAULT 'EN_ESPERA',
    observaciones_gerente TEXT,
    username_gerente VARCHAR(50),
    fecha_solicitud DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_resolucion DATETIME,
    FOREIGN KEY (id_cliente) REFERENCES clientes_info(id_cliente)
);

-- Datos de prueba para sucursales
INSERT INTO sucursales (nombre, direccion, telefono) VALUES
('Sucursal Central', 'Alameda Juan Pablo II, San Salvador', '2222-0001'),
('Sucursal Santa Ana', '4a Av. Norte, Santa Ana', '2222-0002');

-- Datos de prueba para clientes (4 rangos salariales diferentes)
INSERT INTO clientes_info (nombre, dui, salario, telefono, direccion) VALUES
('Juan Antonio Pérez', '12345678-9', 300.00, '7123-4567', 'Col. Escalón, San Salvador'),
('María Luisa García', '98765432-1', 450.00, '7234-5678', 'Col. Jardines, Santa Ana'),
('Carlos Eduardo López', '55544433-2', 750.00, '7345-6789', 'Res. Miraflores, San Miguel'),
('Ana Patricia Martínez', '11122233-4', 1200.00, '7456-7890', 'Col. San Benito, San Salvador');

