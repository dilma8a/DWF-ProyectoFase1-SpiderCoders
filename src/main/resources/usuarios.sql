-- Script de datos de prueba para login (1 usuario por rol)
-- Base: proyectodwf
-- Nota: este script inserta claves en texto plano para pruebas locales.
-- El LoginBean actual permite validar texto plano temporalmente.

USE proyectodwf;

-- Asegurar que los roles base existan
INSERT INTO roles (nombre, descripcion, estado)
VALUES
('Cliente', 'Usuario que consulta informacion y realiza operaciones como cliente', 1),
('Dependiente', 'Usuario que atiende operaciones basicas', 1),
('Cajero', 'Usuario que registra pagos o cobros', 1),
('Gerente de Sucursal', 'Usuario que administra una sucursal', 1),
('Gerente General', 'Usuario con acceso general al sistema', 1)
ON DUPLICATE KEY UPDATE
    descripcion = VALUES(descripcion),
    estado = VALUES(estado);

-- Usuario de prueba: Cliente
INSERT INTO usuarios (id_rol, nombre, correo, clave, estado, ultimo_acceso)
VALUES (
    (SELECT id_rol FROM roles WHERE nombre = 'Cliente' LIMIT 1),
    'cliente',
    'cliente_test@bas.com',
    'cliente123',
    1,
    NULL
)
ON DUPLICATE KEY UPDATE
    id_rol = VALUES(id_rol),
    clave = VALUES(clave),
    estado = VALUES(estado);

-- Usuario de prueba: Dependiente
INSERT INTO usuarios (id_rol, nombre, correo, clave, estado, ultimo_acceso)
VALUES (
    (SELECT id_rol FROM roles WHERE nombre = 'Dependiente' LIMIT 1),
    'dependiente',
    'dependiente_test@bas.com',
    'dependiente123',
    1,
    NULL
)
ON DUPLICATE KEY UPDATE
    id_rol = VALUES(id_rol),
    clave = VALUES(clave),
    estado = VALUES(estado);

-- Usuario de prueba: Cajero
INSERT INTO usuarios (id_rol, nombre, correo, clave, estado, ultimo_acceso)
VALUES (
    (SELECT id_rol FROM roles WHERE nombre = 'Cajero' LIMIT 1),
    'cajero',
    'cajero_test@bas.com',
    'cajero123',
    1,
    NULL
)
ON DUPLICATE KEY UPDATE
    id_rol = VALUES(id_rol),
    clave = VALUES(clave),
    estado = VALUES(estado);

-- Usuario de prueba: Gerente de Sucursal
INSERT INTO usuarios (id_rol, nombre, correo, clave, estado, ultimo_acceso)
VALUES (
    (SELECT id_rol FROM roles WHERE nombre = 'Gerente de Sucursal' LIMIT 1),
    'gerentesucursal',
    'gerente_sucursal_test@bas.com',
    'gerentesucursal123',
    1,
    NULL
)
ON DUPLICATE KEY UPDATE
    id_rol = VALUES(id_rol),
    clave = VALUES(clave),
    estado = VALUES(estado);

-- Usuario de prueba: Gerente General
INSERT INTO usuarios (id_rol, nombre, correo, clave, estado, ultimo_acceso)
VALUES (
    (SELECT id_rol FROM roles WHERE nombre = 'Gerente General' LIMIT 1),
    'gerentegeneral',
    'gerente_general_test@bas.com',
    'gerentegeneral123',
    1,
    NULL
)
ON DUPLICATE KEY UPDATE
    id_rol = VALUES(id_rol),
    clave = VALUES(clave),
    estado = VALUES(estado);

-- Verificacion rapida
SELECT u.id_usuario, u.nombre, u.correo, r.nombre AS rol, u.estado
FROM usuarios u
JOIN roles r ON r.id_rol = u.id_rol
WHERE u.nombre IN (
    'cliente_test',
    'dependiente_test',
    'cajero_test',
    'gerente_sucursal_test',
    'gerente_general_test'
)
ORDER BY r.nombre;
