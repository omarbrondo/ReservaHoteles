import connection.DBConnection;
import repository.HabitacionRepository;
import repository.ReservaRepository;
import model.Reserva;

import static spark.Spark.*;

import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {

        // Crea las tablas si no existen
        DBConnection.initializeDatabase();

        // Aca pongo esto para que busque los archivos del fron
        staticFiles.location("/static");

        // Configuracion del puerto a usar
        port(4568);

        Gson gson = new Gson();

        get("/", (req, res) -> {
            res.redirect("/index.html");
            return null;
        });

        
        // Ruta para obtener las habitaciones libres (por ejemplo, para llenar un combo en el formulario)
        get("/habitaciones", (req, res) -> {
            HabitacionRepository repo = new HabitacionRepository();
            return repo.findHabFree();
        }, gson::toJson);

        // Ruta para procesar el formulario y crear una reserva
        post("/crearReserva", (req, res) -> {
            // Obtenemos los parámetros enviados en el formulario
            String nombre = req.queryParams("nombre");
            String apellido = req.queryParams("apellido");
            String fechaInicio = req.queryParams("fecha_inicio");
            String fechaFin = req.queryParams("fecha_fin");
            // El parámetro "habitacion" es el id de la habitación seleccionada
            int habitacionId = Integer.parseInt(req.queryParams("habitacion"));
            
            // Guardamos la reserva en la base de datos
            Reserva reserva = new Reserva(nombre, apellido, fechaInicio, fechaFin);
            ReservaRepository reservaRepo = new ReservaRepository();
            int reservaId = reservaRepo.save(reserva);
            
            // Actualizamos la habitación, asignándole la reserva y marcándola como 'ocupada'
            HabitacionRepository habRepo = new HabitacionRepository();
            habRepo.updateHabitacionToOccupied(habitacionId, reservaId);
            
            // Redirigimos a la página principal
            res.redirect("/");
            return "";
        });
        
        System.out.println("Servidor iniciado en el puerto 4568");
    }
}
