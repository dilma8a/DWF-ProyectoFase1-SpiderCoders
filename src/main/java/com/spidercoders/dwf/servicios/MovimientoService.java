package com.spidercoders.dwf.servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.spidercoders.dwf.dao.MovimientoDao;
import com.spidercoders.dwf.pojos.Movimiento;

public class MovimientoService {

    private final MovimientoDao movimientoDao = new MovimientoDao();

    public Long contarMovimientos() {
        return movimientoDao.contarTodos();
    }

    public List<Movimiento> listarRecientes(int maxResults) {
        return movimientoDao.listarRecientes(maxResults);
    }

    public List<Movimiento> filtrarMovimientos(Integer idSucursal, String tipoMovimiento, String fechaDesde, String fechaHasta) {
        return movimientoDao.buscarFiltrados(
                idSucursal,
                tipoMovimiento,
                parseFechaInicio(fechaDesde),
                parseFechaFin(fechaHasta),
                100);
    }

    private LocalDateTime parseFechaInicio(String fecha) {
        if (fecha == null || fecha.trim().isEmpty()) {
            return null;
        }
        return LocalDate.parse(fecha.trim()).atStartOfDay();
    }

    private LocalDateTime parseFechaFin(String fecha) {
        if (fecha == null || fecha.trim().isEmpty()) {
            return null;
        }
        return LocalDate.parse(fecha.trim()).atTime(LocalTime.MAX);
    }
}