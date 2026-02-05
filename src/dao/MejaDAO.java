// MejaDAO.java
package dao;

import model.DatabaseConnection;
import model.Meja;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MejaDAO {
    private Connection connection;

    public MejaDAO() {
        connection = DatabaseConnection.getConnection();
    }

    public List<Meja> getAllMejaTersedia() {
        List<Meja> mejaList = new ArrayList<>();
        String query = "SELECT * FROM meja WHERE status = 'tersedia' ORDER BY nomor_meja";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Meja meja = new Meja(
                        rs.getInt("id_meja"),
                        rs.getString("nomor_meja"),
                        rs.getInt("kapasitas"),
                        rs.getString("status")
                );
                mejaList.add(meja);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mejaList;
    }

    public boolean updateStatusMeja(int idMeja, String status) {
        String query = "UPDATE meja SET status = ? WHERE id_meja = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, idMeja);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
