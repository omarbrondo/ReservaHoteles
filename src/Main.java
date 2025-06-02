import connection.DBConnection;
import repository.HabitacionRepository;
import repository.ReservaRepository;
import repository.ReservaHabitacionRepository;

import model.Reserva;

import static spark.Spark.*;

import com.google.gson.Gson;
import connection.DBConnection;
import model.Reserva;
import repository.HabitacionRepository;
import repository.ReservaRepository;
import repository.ReservaHabitacionRepository;

public class Main {
    public static void main(String[] args) {
    	
    	
    	Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Ejecutando shutdown hook: Deteniendo el servidor...");
            stop();
            // Esperar a que Spark se detenga completamente
            awaitStop();
            System.out.println("Servidor detenido.");
        }));
    	
    	
    	

        // Inicializamos la base de datos y las tablas
        DBConnection.initializeDatabase();

        // Configuramos donde están los archivos estáticos (colócalos en src/main/resources/static)
        staticFiles.location("/static");

        // Definimos el puerto en una variable
        int configuredPort = 4568;
        port(configuredPort);

        // Usaremos Gson para manejar JSON
        Gson gson = new Gson();

        // Definición de rutas
        get("/", (req, res) -> {
            res.redirect("/index.html");
            return null;
        });

        // Ruta para obtener las habitaciones libres
        get("/habitaciones", (req, res) -> {
            HabitacionRepository repo = new HabitacionRepository();
            return repo.findHabFree();
        }, gson::toJson);

        // Ruta para procesar el formulario y crear una reserva
        post("/crearReserva", (req, res) -> {
            String nombre = req.queryParams("nombre");
            String apellido = req.queryParams("apellido");
            String fechaInicio = req.queryParams("fecha_inicio");
            String fechaFin = req.queryParams("fecha_fin");
            int habitacionId = Integer.parseInt(req.queryParams("habitacion"));

            Reserva reserva = new Reserva(nombre, apellido, fechaInicio, fechaFin);
            ReservaRepository reservaRepo = new ReservaRepository();
            int reservaId = reservaRepo.save(reserva);

            HabitacionRepository habRepo = new HabitacionRepository();
            habRepo.updateHabitacionToOccupied(habitacionId, reservaId);

            res.redirect("/");
            return "";
        });

        // Ruta para obtener todas las reservas con su habitación asignada
        get("/reservas", (req, res) -> {
            ReservaHabitacionRepository repo = new ReservaHabitacionRepository();
            return repo.findAll();
        }, gson::toJson);

        // Imprimir el puerto configurado
        System.out.println("Servidor iniciado en el puerto " + configuredPort);
    }
}

