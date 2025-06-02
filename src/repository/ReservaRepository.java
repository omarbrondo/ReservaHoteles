package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Reserva;
import connection.DBConnection;

public class ReservaRepository {
    // Guarda una nueva reserva y retorna el id generado
    public int save(Reserva reserva) {
        String sql = "INSERT INTO reserva(nombre, apellido, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?)";
        int generatedId = -1;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, reserva.getNombre());
            pstmt.setString(2, reserva.getApellido());
            pstmt.setString(3, reserva.getFechaInicio());
            pstmt.setString(4, reserva.getFechaFin());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }
}
