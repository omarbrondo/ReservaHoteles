package model;

public class Reserva {
    private int id;
    private String nombre;
    private String apellido;
    private String fechaInicio;
    private String fechaFin;

    // Constructor con id (por si se necesita al leer desde la base)
    public Reserva(int id, String nombre, String apellido, String fechaInicio, String fechaFin) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Constructor sin id para insertar nuevos registros
    public Reserva(String nombre, String apellido, String fechaInicio, String fechaFin) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Getters & Setters
    public int getId() {
        return id;
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
    public void setId(int id) {
        this.id = id;
    }
}
