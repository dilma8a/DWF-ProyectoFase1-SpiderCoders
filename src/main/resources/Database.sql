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

-- =========================
-- SUCURSALES
-- =========================
CREATE TABLE sucursales (
    id_sucursal INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    telefono VARCHAR(20),
    estado TINYINT DEFAULT 1,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- CLIENTES / PRESTAMISTAS
-- =========================
CREATE TABLE clientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NULL,
    dui VARCHAR(10) NOT NULL UNIQUE,
    nombres VARCHAR(80) NOT NULL,
    apellidos VARCHAR(80) NOT NULL,
    telefono VARCHAR(20),
    direccion VARCHAR(200),
    salario DECIMAL(10,2) NOT NULL,
    estado TINYINT DEFAULT 1,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

-- =========================
-- EMPLEADOS DEL BANCO
-- =========================
CREATE TABLE empleados (
    id_empleado INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NULL,
    id_sucursal INT NOT NULL,
    nombres VARCHAR(80) NOT NULL,
    apellidos VARCHAR(80) NOT NULL,
    dui VARCHAR(10) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    cargo VARCHAR(80) NOT NULL,
    salario DECIMAL(10,2),
    estado ENUM('Activo','Inactivo','En espera') DEFAULT 'Activo',
    fecha_contratacion DATE,

    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_sucursal) REFERENCES sucursales(id_sucursal)
);

-- =========================
-- DEPENDIENTES DEL BANCO
-- =========================
CREATE TABLE dependientes (
    id_dependiente INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    nombre_comercio VARCHAR(120) NOT NULL,
    tipo_comercio VARCHAR(80),
    direccion VARCHAR(200),
    telefono VARCHAR(20),
    comision DECIMAL(5,2) DEFAULT 5.00,
    estado TINYINT DEFAULT 1,

    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

-- =========================
-- CUENTAS BANCARIAS
-- =========================
CREATE TABLE cuentas_bancarias (
    id_cuenta INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_sucursal INT NULL,
    numero_cuenta VARCHAR(20) NOT NULL UNIQUE,
    saldo DECIMAL(12,2) DEFAULT 0.00,
    estado ENUM('Activa','Inactiva','Bloqueada') DEFAULT 'Activa',
    fecha_apertura DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente),
    FOREIGN KEY (id_sucursal) REFERENCES sucursales(id_sucursal)
);

-- =========================
-- MOVIMIENTOS: DEPÓSITOS, RETIROS, TRANSFERENCIAS
-- =========================
CREATE TABLE movimientos (
    id_movimiento INT AUTO_INCREMENT PRIMARY KEY,
    id_cuenta INT NOT NULL,
    id_usuario INT NOT NULL,
    tipo_movimiento ENUM('Deposito','Retiro','Transferencia enviada','Transferencia recibida') NOT NULL,
    monto DECIMAL(12,2) NOT NULL,
    saldo_anterior DECIMAL(12,2) NOT NULL,
    saldo_resultante DECIMAL(12,2) NOT NULL,
    descripcion VARCHAR(200),
    fecha_movimiento DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_cuenta) REFERENCES cuentas_bancarias(id_cuenta),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

-- =========================
-- TRANSFERENCIAS ENTRE CUENTAS
-- =========================
CREATE TABLE transferencias (
    id_transferencia INT AUTO_INCREMENT PRIMARY KEY,
    id_cuenta_origen INT NOT NULL,
    id_cuenta_destino INT NOT NULL,
    id_usuario INT NOT NULL,
    monto DECIMAL(12,2) NOT NULL,
    descripcion VARCHAR(200),
    fecha_transferencia DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('Realizada','Rechazada') DEFAULT 'Realizada',

    FOREIGN KEY (id_cuenta_origen) REFERENCES cuentas_bancarias(id_cuenta),
    FOREIGN KEY (id_cuenta_destino) REFERENCES cuentas_bancarias(id_cuenta),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

-- =========================
-- CASOS DE PRÉSTAMOS
-- =========================
CREATE TABLE casos_prestamos (
    id_caso INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_cajero INT NOT NULL,
    monto_solicitado DECIMAL(12,2) NOT NULL,
    interes DECIMAL(5,2) NOT NULL,
    cuota_mensual DECIMAL(12,2) NOT NULL,
    plazo_anios INT NOT NULL,
    estado ENUM('En espera','Aprobado','Rechazado') DEFAULT 'En espera',
    observaciones VARCHAR(300),
    fecha_apertura DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_resolucion DATETIME NULL,

    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente),
    FOREIGN KEY (id_cajero) REFERENCES empleados(id_empleado)
);

-- =========================
-- PRÉSTAMOS APROBADOS
-- =========================
CREATE TABLE prestamos (
    id_prestamo INT AUTO_INCREMENT PRIMARY KEY,
    id_caso INT NOT NULL,
    id_cliente INT NOT NULL,
    monto_aprobado DECIMAL(12,2) NOT NULL,
    interes DECIMAL(5,2) NOT NULL,
    cuota_mensual DECIMAL(12,2) NOT NULL,
    plazo_anios INT NOT NULL,
    saldo_pendiente DECIMAL(12,2) NOT NULL,
    estado ENUM('Activo','Pagado','En mora') DEFAULT 'Activo',
    fecha_aprobacion DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_caso) REFERENCES casos_prestamos(id_caso),
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente)
);

-- =========================
-- PAGOS DE PRÉSTAMOS
-- =========================
CREATE TABLE pagos_prestamos (
    id_pago INT AUTO_INCREMENT PRIMARY KEY,
    id_prestamo INT NOT NULL,
    monto_pagado DECIMAL(12,2) NOT NULL,
    fecha_pago DATETIME DEFAULT CURRENT_TIMESTAMP,
    observacion VARCHAR(200),

    FOREIGN KEY (id_prestamo) REFERENCES prestamos(id_prestamo)
);

-- =========================
-- ACCIONES DE PERSONAL
-- =========================
CREATE TABLE acciones_personal (
    id_accion INT AUTO_INCREMENT PRIMARY KEY,
    id_empleado INT NOT NULL,
    id_gerente_sucursal INT NOT NULL,
    id_gerente_general INT NULL,
    tipo_accion ENUM('Contratacion','Baja','Cambio de cargo','Cambio de sucursal') NOT NULL,
    descripcion VARCHAR(300),
    estado ENUM('En espera','Aprobada','Rechazada') DEFAULT 'En espera',
    fecha_solicitud DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_resolucion DATETIME NULL,

    FOREIGN KEY (id_empleado) REFERENCES empleados(id_empleado),
    FOREIGN KEY (id_gerente_sucursal) REFERENCES empleados(id_empleado),
    FOREIGN KEY (id_gerente_general) REFERENCES empleados(id_empleado)
);

-- =========================
-- COMISIONES DE DEPENDIENTES
-- =========================
CREATE TABLE comisiones_dependientes (
    id_comision INT AUTO_INCREMENT PRIMARY KEY,
    id_dependiente INT NOT NULL,
    id_movimiento INT NOT NULL,
    monto_operacion DECIMAL(12,2) NOT NULL,
    porcentaje_comision DECIMAL(5,2) DEFAULT 5.00,
    monto_comision DECIMAL(12,2) NOT NULL,
    fecha_comision DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_dependiente) REFERENCES dependientes(id_dependiente),
    FOREIGN KEY (id_movimiento) REFERENCES movimientos(id_movimiento)
);
