package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ReservaHabitacion;
import connection.DBConnection;

public class ReservaHabitacionRepository {
    public List<ReservaHabitacion> findAll() {
        List<ReservaHabitacion> reservas = new ArrayList<>();
        // Realizamos un LEFT JOIN para incluir reservas que tienen habitación asignada
        String sql = "SELECT r.id as reservaId, r.nombre, r.apellido, r.fecha_inicio, r.fecha_fin, " +
                     "h.nombre_habitacion, h.estado " +
                     "FROM reserva r LEFT JOIN habitacion h ON r.id = h.reserva_id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ReservaHabitacion resHab = new ReservaHabitacion(
                    rs.getInt("reservaId"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("fecha_inicio"),
                    rs.getString("fecha_fin"),
                    rs.getString("nombre_habitacion"),
                    rs.getString("estado")
                );
                reservas.add(resHab);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }
}
