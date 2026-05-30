package com.spidercoders.dwf.pojos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "acciones_personal")
public class AccionPersonal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_accion")
    private Integer idAccion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado empleado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_gerente_sucursal", nullable = false)
    private Empleado gerenteSucursal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_gerente_general")
    private Empleado gerenteGeneral;

    @Column(name = "tipo_accion", nullable = false, length = 60)
    private String tipoAccion;

    @Column(name = "descripcion", length = 300)
    private String descripcion;

    @Column(name = "estado")
    private String estado;

    @Column(name = "fecha_solicitud", insertable = false, updatable = false)
    private LocalDateTime fechaSolicitud;

    @Column(name = "fecha_resolucion")
    private LocalDateTime fechaResolucion;

    public Integer getIdAccion() {
        return idAccion;
    }

    public void setIdAccion(Integer idAccion) {
        this.idAccion = idAccion;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Empleado getGerenteSucursal() {
        return gerenteSucursal;
    }

    public void setGerenteSucursal(Empleado gerenteSucursal) {
        this.gerenteSucursal = gerenteSucursal;
    }

    public Empleado getGerenteGeneral() {
        return gerenteGeneral;
    }

    public void setGerenteGeneral(Empleado gerenteGeneral) {
        this.gerenteGeneral = gerenteGeneral;
    }

    public String getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(String tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public String getFechaSolicitudTexto() {
        return fechaSolicitud == null ? "" : fechaSolicitud.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public LocalDateTime getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(LocalDateTime fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }
}