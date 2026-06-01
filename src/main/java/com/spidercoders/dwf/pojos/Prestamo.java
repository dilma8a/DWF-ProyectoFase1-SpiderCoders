package com.spidercoders.dwf.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestamo")
    private Integer idPrestamo;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private ClienteInfo cliente;

    @Column(name = "username_cajero", length = 50)
    private String usernameCajero;

    @Column(name = "monto_solicitado", precision = 10, scale = 2)
    private BigDecimal montoSolicitado;

    @Column(name = "tasa_interes", precision = 5, scale = 2)
    private BigDecimal tasaInteres;

    @Column(name = "cuota_mensual", precision = 10, scale = 2)
    private BigDecimal cuotaMensual;

    @Column(name = "plazo_meses")
    private Integer plazoMeses;

    @Column(name = "anios_pago", precision = 5, scale = 2)
    private BigDecimal aniosPago;

    @Column(length = 255)
    private String proposito;

    @Column
    private String estado;

    @Column(name = "observaciones_gerente", columnDefinition = "TEXT")
    private String observacionesGerente;

    @Column(name = "username_gerente", length = 50)
    private String usernameGerente;

    @Column(name = "fecha_solicitud")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSolicitud;

    @Column(name = "fecha_resolucion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaResolucion;

    public Integer getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(Integer idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public ClienteInfo getCliente() {
        return cliente;
    }

    public void setCliente(ClienteInfo cliente) {
        this.cliente = cliente;
    }

    public String getUsernameCajero() {
        return usernameCajero;
    }

    public void setUsernameCajero(String usernameCajero) {
        this.usernameCajero = usernameCajero;
    }

    public BigDecimal getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(BigDecimal montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }

    public BigDecimal getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(BigDecimal tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public BigDecimal getCuotaMensual() {
        return cuotaMensual;
    }

    public void setCuotaMensual(BigDecimal cuotaMensual) {
        this.cuotaMensual = cuotaMensual;
    }

    public Integer getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(Integer plazoMeses) {
        this.plazoMeses = plazoMeses;
    }

    public BigDecimal getAniosPago() {
        return aniosPago;
    }

    public void setAniosPago(BigDecimal aniosPago) {
        this.aniosPago = aniosPago;
    }

    public String getProposito() {
        return proposito;
    }

    public void setProposito(String proposito) {
        this.proposito = proposito;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacionesGerente() {
        return observacionesGerente;
    }

    public void setObservacionesGerente(String observacionesGerente) {
        this.observacionesGerente = observacionesGerente;
    }

    public String getUsernameGerente() {
        return usernameGerente;
    }

    public void setUsernameGerente(String usernameGerente) {
        this.usernameGerente = usernameGerente;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Date getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(Date fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }
}
