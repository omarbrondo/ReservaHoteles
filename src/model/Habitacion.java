package model;

public class Habitacion {
    private int id;
    private String nombreHabitacion;
    private Integer reservaId; // Puede ser null si no hay reserva
    private String estado; // "ocupada" o "libre"

    // Constructor con id
    public Habitacion(int id, String nombreHabitacion, Integer reservaId, String estado) {
        this.id = id;
        this.nombreHabitacion = nombreHabitacion;
        this.reservaId = reservaId;
        this.estado = estado;
    }

    // Constructor sin id (para insertar nuevos registros)
    public Habitacion(String nombreHabitacion, String estado) {
        this.nombreHabitacion = nombreHabitacion;
        this.estado = estado;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }
    public String getNombreHabitacion() {
        return nombreHabitacion;
    }
    public Integer getReservaId() {
        return reservaId;
    }
    public String getEstado() {
        return estado;
    }
    public void setReservaId(Integer reservaId) {
        this.reservaId = reservaId;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
