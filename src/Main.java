import connection.DBConnection;
import repository.HabitacionRepository;
import repository.ReservaRepository;
import model.Reserva;

import static spark.Spark.*;

import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {

        // Inicializamos la base de datos y las tablas
        DBConnection.initializeDatabase();

        // Configuramos donde están los archivos estáticos (HTML, CSS, etc.)
        staticFiles.location("/static");

        // Definimos el puerto en una variable
        int configuredPort = 4568;
        // Configuramos el puerto del servidor usando la variable
        port(configuredPort);

        // Agregamos un shutdown hook para detener el servidor al cerrar la aplicación
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Deteniendo el servidor...");
            stop();
            awaitStop();
        }));

        // Usaremos Gson para manejar JSON
        Gson gson = new Gson();

        // Ruta para redirigir la raíz a index.html
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
        
        System.out.println("Servidor iniciado en el puerto " + configuredPort);
    }
}
