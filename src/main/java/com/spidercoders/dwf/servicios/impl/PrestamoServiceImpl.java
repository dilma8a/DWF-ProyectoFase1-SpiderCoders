package com.spidercoders.dwf.servicios.impl;

import com.spidercoders.dwf.dao.PrestamoDao;
import com.spidercoders.dwf.pojos.Prestamo;
import com.spidercoders.dwf.servicios.PrestamoService;

import java.util.List;

public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoDao prestamoDao;

    public PrestamoServiceImpl() {
        this.prestamoDao = new PrestamoDao();
    }

    @Override
    public List<Prestamo> findAll() {
        return prestamoDao.findAll();
    }

    @Override
    public List<Prestamo> findByCajero(String usernameCajero) {
        return prestamoDao.findByCajero(usernameCajero);
    }

    @Override
    public List<Prestamo> findEnEspera() {
        return prestamoDao.findEnEspera();
    }

    @Override
    public Prestamo findById(Integer id) {
        return prestamoDao.findById(id);
    }

    @Override
    public void save(Prestamo prestamo) {
        prestamoDao.save(prestamo);
    }

    @Override
    public void update(Prestamo prestamo) {
        prestamoDao.update(prestamo);
    }

    public static double calcularMontoMaximo(double salario) {
        if (salario < 365) return 10000;
        if (salario < 600) return 25000;
        if (salario < 900) return 35000;
        return 50000;
    }

    public static double calcularTasaInteres(double salario) {
        if (salario < 365) return 3.0;
        if (salario < 600) return 3.0;
        if (salario < 900) return 4.0;
        return 5.0;
    }

    public static double calcularCuotaMensual(double monto, double tasaAnualPct, int plazoMeses) {
        double r = tasaAnualPct / 100.0 / 12.0;
        if (r == 0) return monto / plazoMeses;
        return monto * (r * Math.pow(1 + r, plazoMeses)) / (Math.pow(1 + r, plazoMeses) - 1);
    }

    public static int calcularPlazoMinimo(double monto, double tasaAnualPct, double salario) {
        double maxCuota = salario * 0.30;
        double r = tasaAnualPct / 100.0 / 12.0;
        if (r == 0) return (int) Math.ceil(monto / maxCuota);
        double numerator = Math.log(maxCuota / (maxCuota - monto * r));
        double denominator = Math.log(1 + r);
        return (int) Math.ceil(numerator / denominator);
    }
}
