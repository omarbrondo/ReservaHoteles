package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Habitacion;
import connection.DBConnection;

public class HabitacionRepository {
    // Retorna la lista de habitaciones con estado 'libre'
    public List<Habitacion> findHabFree() {
        List<Habitacion> habitacionList = new ArrayList<>();
        String sql = "SELECT * FROM habitacion WHERE estado = 'libre'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while(rs.next()){
                Habitacion hab = new Habitacion(
                    rs.getInt("id"),
                    rs.getString("nombre_habitacion"),
                    rs.getObject("reserva_id") != null ? rs.getInt("reserva_id") : null,
                    rs.getString("estado")
                );
                habitacionList.add(hab);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return habitacionList;
    }
    
    // Actualiza la habitación asignándole una reserva y cambiando su estado a 'ocupada'
    public void updateHabitacionToOccupied(int habitacionId, int reservaId) {
        String sql = "UPDATE habitacion SET reserva_id = ?, estado = 'ocupada' WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reservaId);
            pstmt.setInt(2, habitacionId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
