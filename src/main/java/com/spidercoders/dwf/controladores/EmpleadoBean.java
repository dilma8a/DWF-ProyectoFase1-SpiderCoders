package com.spidercoders.dwf.controladores;

import com.spidercoders.dwf.pojos.AccionPersonal;
import com.spidercoders.dwf.pojos.Empleado;
import com.spidercoders.dwf.servicios.impl.EmpleadoServiceImpl;
import com.spidercoders.dwf.utilidades.JPAUtil;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "empleadoBean")
@SessionScoped
public class EmpleadoBean {

    // Campos del formulario de registro
    private String nombre;
    private String correo;
    private String clave;
    private String tipoEmpleado; // CAJERO, LIMPIEZA, SECRETARIA, ASESOR_MESA
    private double salario;

    // Lista de empleados
    private List<Empleado> empleados;

    // Mensaje de resultado
    private String mensajeResultado;

    private final EmpleadoServiceImpl empleadoService = new EmpleadoServiceImpl();

    // -----------------------------------------------------------------------
    // Método registrarEmpleado
    // -----------------------------------------------------------------------
    public void registrarEmpleado() {
        // Validaciones
        if (nombre == null || nombre.trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "El nombre del empleado es requerido."));
            return;
        }
        if (correo == null || correo.trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "El correo del empleado es requerido."));
            return;
        }
        if (clave == null || clave.trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "La clave del empleado es requerida."));
            return;
        }
        if (tipoEmpleado == null || tipoEmpleado.trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "El tipo de empleado es requerido."));
            return;
        }

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String usernameGerente = (String) externalContext.getSessionMap().get("username");

        // Crear entidad Empleado
        Empleado empleado = new Empleado();
        empleado.setNombre(nombre.trim());
        empleado.setCorreo(correo.trim());
        empleado.setClave(clave.trim());
        empleado.setTipoEmpleado(tipoEmpleado.trim());
        empleado.setSalario(BigDecimal.valueOf(salario));
        empleado.setEstado("ACTIVO");
        empleado.setFechaIngreso(new Date());
        empleado.setUsernameGerente(usernameGerente);

        empleadoService.save(empleado);

        // Crear AccionPersonal tipo ALTA usando JPAUtil
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            AccionPersonal accion = new AccionPersonal();
            accion.setEmpleado(empleado);
            accion.setUsernameGerente(usernameGerente);
            accion.setTipoAccion("ALTA");
            accion.setEstado("EN_ESPERA");
            accion.setFechaSolicitud(new Date());
            em.persist(accion);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
        } finally {
            em.close();
        }

        // Limpiar formulario
        limpiarFormulario();

        mensajeResultado = "Empleado registrado. Acción de personal enviada para aprobación del Gerente General.";

        // Recargar lista
        empleados = empleadoService.findAll();
        if (empleados == null) {
            empleados = new ArrayList<>();
        }
    }

    // -----------------------------------------------------------------------
    // Método cargarEmpleados
    // -----------------------------------------------------------------------
    public void cargarEmpleados() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String username = (String) externalContext.getSessionMap().get("username");
        empleados = empleadoService.findByGerente(username);
        if (empleados == null) {
            empleados = new ArrayList<>();
        }
    }

    // -----------------------------------------------------------------------
    // Método darDeBaja
    // -----------------------------------------------------------------------
    public void darDeBaja(Integer idEmpleado) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String usernameGerente = (String) externalContext.getSessionMap().get("username");

        Empleado emp = empleadoService.findById(idEmpleado);
        if (emp == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "No se encontró el empleado con ID: " + idEmpleado));
            return;
        }

        emp.setEstado("INACTIVO");
        empleadoService.update(emp);

        // Crear AccionPersonal tipo BAJA usando JPAUtil
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            AccionPersonal accion = new AccionPersonal();
            accion.setEmpleado(emp);
            accion.setUsernameGerente(usernameGerente);
            accion.setTipoAccion("BAJA");
            accion.setEstado("EN_ESPERA");
            accion.setFechaSolicitud(new Date());
            em.persist(accion);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
        } finally {
            em.close();
        }

        // Recargar empleados
        cargarEmpleados();
    }

    // -----------------------------------------------------------------------
    // Método limpiarFormulario
    // -----------------------------------------------------------------------
    public void limpiarFormulario() {
        nombre = null;
        correo = null;
        clave = null;
        tipoEmpleado = null;
        salario = 0;
    }

    // -----------------------------------------------------------------------
    // Getters y Setters
    // -----------------------------------------------------------------------

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(String tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public String getMensajeResultado() {
        return mensajeResultado;
    }

    public void setMensajeResultado(String mensajeResultado) {
        this.mensajeResultado = mensajeResultado;
    }
}
