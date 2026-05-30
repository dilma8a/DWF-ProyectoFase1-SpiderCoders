USE proyectodwf;

-- Sucursales base para pruebas
INSERT INTO sucursales (nombre, direccion, telefono, estado)
SELECT 'Sucursal Central', 'Av. Independencia, San Salvador', '2222-1111', 1
WHERE NOT EXISTS (
    SELECT 1 FROM sucursales WHERE nombre = 'Sucursal Central'
);

INSERT INTO sucursales (nombre, direccion, telefono, estado)
SELECT 'Sucursal Santa Tecla', 'Bulevar Sur, La Libertad', '2222-2222', 1
WHERE NOT EXISTS (
    SELECT 1 FROM sucursales WHERE nombre = 'Sucursal Santa Tecla'
);

-- Empleados base: gerente general, gerente de sucursal y cajero
INSERT INTO empleados (id_usuario, id_sucursal, nombres, apellidos, dui, telefono, cargo, salario, estado, fecha_contratacion)
SELECT u.id_usuario, s.id_sucursal, 'Luis', 'Hernandez', '00000001-1', '7000-1001', 'Gerente General', 2500.00, 'Activo', '2025-01-10'
FROM usuarios u
JOIN sucursales s ON s.nombre = 'Sucursal Central'
WHERE u.nombre = 'gerentegeneral'
  AND NOT EXISTS (SELECT 1 FROM empleados e WHERE e.dui = '00000001-1');

INSERT INTO empleados (id_usuario, id_sucursal, nombres, apellidos, dui, telefono, cargo, salario, estado, fecha_contratacion)
SELECT u.id_usuario, s.id_sucursal, 'Marta', 'Lopez', '00000002-2', '7000-1002', 'Gerente de Sucursal', 1800.00, 'Activo', '2025-01-12'
FROM usuarios u
JOIN sucursales s ON s.nombre = 'Sucursal Santa Tecla'
WHERE u.nombre = 'gerentesucursal'
  AND NOT EXISTS (SELECT 1 FROM empleados e WHERE e.dui = '00000002-2');

INSERT INTO empleados (id_usuario, id_sucursal, nombres, apellidos, dui, telefono, cargo, salario, estado, fecha_contratacion)
SELECT u.id_usuario, s.id_sucursal, 'Carlos', 'Perez', '00000003-3', '7000-1003', 'Cajero', 900.00, 'Activo', '2025-01-20'
FROM usuarios u
JOIN sucursales s ON s.nombre = 'Sucursal Central'
WHERE u.nombre = 'cajero'
  AND NOT EXISTS (SELECT 1 FROM empleados e WHERE e.dui = '00000003-3');

INSERT INTO empleados (id_usuario, id_sucursal, nombres, apellidos, dui, telefono, cargo, salario, estado, fecha_contratacion)
SELECT u.id_usuario, s.id_sucursal, 'Ana', 'Ramos', '00000004-4', '7000-1004', 'Dependiente', 750.00, 'Activo', '2025-02-01'
FROM usuarios u
JOIN sucursales s ON s.nombre = 'Sucursal Central'
WHERE u.nombre = 'dependiente'
  AND NOT EXISTS (SELECT 1 FROM empleados e WHERE e.dui = '00000004-4');

-- Cliente de prueba y su cuenta
INSERT INTO clientes (id_usuario, dui, nombres, apellidos, telefono, direccion, salario, estado)
SELECT u.id_usuario, '01234567-8', 'Jose', 'Martinez', '7777-1111', 'Colonia Escalon, San Salvador', 1200.00, 1
FROM usuarios u
WHERE u.nombre = 'cliente'
  AND NOT EXISTS (SELECT 1 FROM clientes c WHERE c.dui = '01234567-8');

INSERT INTO cuentas_bancarias (id_cliente, id_sucursal, numero_cuenta, saldo, estado)
SELECT c.id_cliente, s.id_sucursal, '000123456789', 1540.75, 'Activa'
FROM clientes c
JOIN sucursales s ON s.nombre = 'Sucursal Central'
WHERE c.dui = '01234567-8'
  AND NOT EXISTS (SELECT 1 FROM cuentas_bancarias cb WHERE cb.numero_cuenta = '000123456789');

-- Movimientos de prueba
INSERT INTO movimientos (id_cuenta, id_usuario, tipo_movimiento, monto, saldo_anterior, saldo_resultante, descripcion)
SELECT cb.id_cuenta, u.id_usuario, 'Deposito', 250.00, 1290.75, 1540.75, 'Deposito en ventanilla'
FROM cuentas_bancarias cb
JOIN usuarios u ON u.nombre = 'cajero'
WHERE cb.numero_cuenta = '000123456789'
  AND NOT EXISTS (
      SELECT 1 FROM movimientos m
      WHERE m.id_cuenta = cb.id_cuenta AND m.tipo_movimiento = 'Deposito' AND m.monto = 250.00 AND m.descripcion = 'Deposito en ventanilla'
  );

INSERT INTO movimientos (id_cuenta, id_usuario, tipo_movimiento, monto, saldo_anterior, saldo_resultante, descripcion)
SELECT cb.id_cuenta, u.id_usuario, 'Retiro', 100.00, 1540.75, 1440.75, 'Retiro por caja'
FROM cuentas_bancarias cb
JOIN usuarios u ON u.nombre = 'cajero'
WHERE cb.numero_cuenta = '000123456789'
  AND NOT EXISTS (
      SELECT 1 FROM movimientos m
      WHERE m.id_cuenta = cb.id_cuenta AND m.tipo_movimiento = 'Retiro' AND m.monto = 100.00 AND m.descripcion = 'Retiro por caja'
  );

-- Acciones de personal para el módulo del gerente general
INSERT INTO acciones_personal (id_empleado, id_gerente_sucursal, id_gerente_general, tipo_accion, descripcion, estado)
SELECT e_cajero.id_empleado, e_gs.id_empleado, NULL, 'Cambio de cargo', 'Actualizar cargo de cajero a supervisor de caja', 'En espera'
FROM empleados e_cajero
JOIN empleados e_gs ON e_gs.cargo = 'Gerente de Sucursal'
WHERE e_cajero.dui = '00000003-3'
  AND NOT EXISTS (
      SELECT 1 FROM acciones_personal a
      WHERE a.id_empleado = e_cajero.id_empleado
        AND a.tipo_accion = 'Cambio de cargo'
        AND a.estado = 'En espera'
  )
LIMIT 1;

INSERT INTO acciones_personal (id_empleado, id_gerente_sucursal, id_gerente_general, tipo_accion, descripcion, estado)
SELECT e_dep.id_empleado, e_gs.id_empleado, NULL, 'Contratacion', 'Solicitar contratacion de dependiente adicional', 'En espera'
FROM empleados e_dep
JOIN empleados e_gs ON e_gs.cargo = 'Gerente de Sucursal'
WHERE e_dep.dui = '00000004-4'
  AND NOT EXISTS (
      SELECT 1 FROM acciones_personal a
      WHERE a.id_empleado = e_dep.id_empleado
        AND a.tipo_accion = 'Contratacion'
        AND a.estado = 'En espera'
  )
LIMIT 1;
