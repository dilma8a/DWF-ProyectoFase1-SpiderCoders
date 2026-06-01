package com.spidercoders.dwf.controladores;

import com.spidercoders.dwf.pojos.ClienteInfo;
import com.spidercoders.dwf.pojos.Prestamo;
import com.spidercoders.dwf.servicios.impl.ClienteInfoServiceImpl;
import com.spidercoders.dwf.servicios.impl.PrestamoServiceImpl;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "prestamoBean")
@SessionScoped
public class PrestamoBean {

    // --- Campos del formulario de búsqueda ---
    private String clienteDui;
    private ClienteInfo clienteEncontrado;

    // --- Campos calculados (se muestran en UI) ---
    private double montoMaximo;
    private double tasaInteres;

    // --- Campos de entrada del préstamo ---
    private double montoSolicitado;
    private String proposito;

    // --- Campos calculados del préstamo ---
    private double cuotaMensual;
    private int plazoMeses;
    private double aniosPago;

    // --- Lista de préstamos del cajero ---
    private List<Prestamo> misPrestamos;

    // --- Mensaje de resultado ---
    private String mensajeResultado;

    private final ClienteInfoServiceImpl clienteService = new ClienteInfoServiceImpl();
    private final PrestamoServiceImpl prestamoService = new PrestamoServiceImpl();

    // -----------------------------------------------------------------------
    // Método buscarCliente
    // -----------------------------------------------------------------------
    public void buscarCliente() {
        clienteEncontrado = null;
        montoMaximo = 0;
        tasaInteres = 0;

        if (clienteDui == null || clienteDui.trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Advertencia", "Ingrese un DUI para buscar."));
            return;
        }

        ClienteInfo cliente = clienteService.findByDui(clienteDui.trim());

        if (cliente == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "No encontrado", "No se encontró cliente con DUI: " + clienteDui));
        } else {
            double salario = cliente.getSalario() != null ? cliente.getSalario().doubleValue() : 0.0;
            montoMaximo = PrestamoServiceImpl.calcularMontoMaximo(salario);
            tasaInteres = PrestamoServiceImpl.calcularTasaInteres(salario);
            clienteEncontrado = cliente;
        }
    }

    // -----------------------------------------------------------------------
    // Método calcularPrestamo
    // -----------------------------------------------------------------------
    public void calcularPrestamo() {
        if (clienteEncontrado == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "Debe buscar y seleccionar un cliente primero."));
            return;
        }

        if (montoSolicitado <= 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "El monto solicitado debe ser mayor a 0."));
            return;
        }

        if (montoSolicitado > montoMaximo) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "El monto solicitado supera el límite permitido. Monto máximo: $" +
                        String.format("%.2f", montoMaximo)));
            return;
        }

        double salario = clienteEncontrado.getSalario() != null
            ? clienteEncontrado.getSalario().doubleValue() : 0.0;

        plazoMeses = PrestamoServiceImpl.calcularPlazoMinimo(montoSolicitado, tasaInteres, salario);
        cuotaMensual = PrestamoServiceImpl.calcularCuotaMensual(montoSolicitado, tasaInteres, plazoMeses);

        // Redondear aniosPago a 2 decimales
        aniosPago = BigDecimal.valueOf(plazoMeses / 12.0)
            .setScale(2, RoundingMode.HALF_UP)
            .doubleValue();

        // Validación de cuota no mayor al 30% del salario
        if (cuotaMensual > salario * 0.30) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Advertencia",
                    "La cuota mensual calculada ($" + String.format("%.2f", cuotaMensual) +
                        ") supera el 30% del salario del cliente."));
        }
    }

    // -----------------------------------------------------------------------
    // Método confirmarApertura
    // -----------------------------------------------------------------------
    public String confirmarApertura() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String username = (String) externalContext.getSessionMap().get("username");

        Prestamo prestamo = new Prestamo();
        prestamo.setCliente(clienteEncontrado);
        prestamo.setUsernameCajero(username);
        prestamo.setMontoSolicitado(BigDecimal.valueOf(montoSolicitado));
        prestamo.setTasaInteres(BigDecimal.valueOf(tasaInteres));
        prestamo.setCuotaMensual(BigDecimal.valueOf(cuotaMensual));
        prestamo.setPlazoMeses(plazoMeses);
        prestamo.setAniosPago(BigDecimal.valueOf(aniosPago));
        prestamo.setProposito(proposito);
        prestamo.setEstado("EN_ESPERA");
        prestamo.setFechaSolicitud(new Date());

        prestamoService.save(prestamo);

        // Limpiar formulario
        limpiarFormulario();

        mensajeResultado = "Solicitud de préstamo registrada exitosamente. Estado: EN ESPERA de aprobación.";

        return "/views/secure/cajero/prestamos-lista.xhtml?faces-redirect=true";
    }

    // -----------------------------------------------------------------------
    // Método cargarMisPrestamos
    // -----------------------------------------------------------------------
    public void cargarMisPrestamos() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String username = (String) externalContext.getSessionMap().get("username");
        misPrestamos = prestamoService.findByCajero(username);
        if (misPrestamos == null) {
            misPrestamos = new ArrayList<>();
        }
    }

    // -----------------------------------------------------------------------
    // Método limpiarFormulario
    // -----------------------------------------------------------------------
    public void limpiarFormulario() {
        clienteDui = null;
        clienteEncontrado = null;
        montoMaximo = 0;
        tasaInteres = 0;
        montoSolicitado = 0;
        proposito = "";
        cuotaMensual = 0;
        plazoMeses = 0;
        aniosPago = 0;
    }

    // -----------------------------------------------------------------------
    // Getters y Setters
    // -----------------------------------------------------------------------

    public String getClienteDui() {
        return clienteDui;
    }

    public void setClienteDui(String clienteDui) {
        this.clienteDui = clienteDui;
    }

    public ClienteInfo getClienteEncontrado() {
        return clienteEncontrado;
    }

    public void setClienteEncontrado(ClienteInfo clienteEncontrado) {
        this.clienteEncontrado = clienteEncontrado;
    }

    public double getMontoMaximo() {
        return montoMaximo;
    }

    public void setMontoMaximo(double montoMaximo) {
        this.montoMaximo = montoMaximo;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public double getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(double montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }

    public String getProposito() {
        return proposito;
    }

    public void setProposito(String proposito) {
        this.proposito = proposito;
    }

    public double getCuotaMensual() {
        return cuotaMensual;
    }

    public void setCuotaMensual(double cuotaMensual) {
        this.cuotaMensual = cuotaMensual;
    }

    public int getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(int plazoMeses) {
        this.plazoMeses = plazoMeses;
    }

    public double getAniosPago() {
        return aniosPago;
    }

    public void setAniosPago(double aniosPago) {
        this.aniosPago = aniosPago;
    }

    public List<Prestamo> getMisPrestamos() {
        return misPrestamos;
    }

    public void setMisPrestamos(List<Prestamo> misPrestamos) {
        this.misPrestamos = misPrestamos;
    }

    public String getMensajeResultado() {
        return mensajeResultado;
    }

    public void setMensajeResultado(String mensajeResultado) {
        this.mensajeResultado = mensajeResultado;
    }
}
