package model;

public class ReservaHabitacion {
    private int reservaId;
    private String nombre;
    private String apellido;
    private String fechaInicio;
    private String fechaFin;
    private String nombreHabitacion;
    private String estado;

    public ReservaHabitacion(int reservaId, String nombre, String apellido,
                             String fechaInicio, String fechaFin,
                             String nombreHabitacion, String estado) {
        this.reservaId = reservaId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.nombreHabitacion = nombreHabitacion;
        this.estado = estado;
    }
    // Getters y setters según necesites
    public int getReservaId() {
        return reservaId;
    }
    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public String getFechaInicio() {
        return fechaInicio;
    }
    public String getFechaFin() {
        return fechaFin;
    }
    public String getNombreHabitacion() {
        return nombreHabitacion;
    }
    public String getEstado() {
        return estado;
    }
}
