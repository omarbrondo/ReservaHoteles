package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    // URL de la base SQLite; se crea el archivo hotel.db en el directorio raíz del proyecto
    private static final String DB_URL = "jdbc:sqlite:hotel.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        String sqlReserva = "CREATE TABLE IF NOT EXISTS reserva ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombre TEXT NOT NULL, "
                + "apellido TEXT NOT NULL, "
                + "fecha_inicio TEXT NOT NULL, "
                + "fecha_fin TEXT NOT NULL"
                + ");";
        String sqlHabitacion = "CREATE TABLE IF NOT EXISTS habitacion ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombre_habitacion TEXT NOT NULL, "
                + "reserva_id INTEGER, "
                + "estado TEXT NOT NULL"
                + ");";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            // Crear tablas
            stmt.execute(sqlReserva);
            stmt.execute(sqlHabitacion);

            // Insertar algunas habitaciones de ejemplo si la tabla está vacía.
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM habitacion");
            if(rs.next() && rs.getInt("count") == 0) {
                String insert1 = "INSERT INTO habitacion (nombre_habitacion, estado) VALUES ('Habitación 101', 'libre')";
                String insert2 = "INSERT INTO habitacion (nombre_habitacion, estado) VALUES ('Habitación 102', 'libre')";
                stmt.execute(insert1);
                stmt.execute(insert2);
            }
            System.out.println("Tablas creadas e inicializadas correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
