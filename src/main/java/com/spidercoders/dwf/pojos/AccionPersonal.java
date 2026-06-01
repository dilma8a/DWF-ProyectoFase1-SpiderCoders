package com.spidercoders.dwf.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "acciones_personal")
public class AccionPersonal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_accion")
    private Integer idAccion;

    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;

    @Column(name = "username_gerente", length = 50)
    private String usernameGerente;

    @Column(name = "tipo_accion")
    private String tipoAccion;

    @Column
    private String estado;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "fecha_solicitud")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSolicitud;

    @Column(name = "fecha_resolucion")
    private LocalDateTime fechaResolucion;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_gerente_general")
    private Empleado gerenteGeneral;

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

    public String getUsernameGerente() {
        return usernameGerente;
    }

    public void setUsernameGerente(String usernameGerente) {
        this.usernameGerente = usernameGerente;
    }

    public String getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(String tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public LocalDateTime getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(LocalDateTime fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }
    
    public Empleado getGerenteGeneral() {
        return gerenteGeneral;
    }
    
    public void setGerenteGeneral(Empleado gerenteGeneral) {
        this.gerenteGeneral = gerenteGeneral;
    }
}