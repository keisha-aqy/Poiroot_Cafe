// MenuDAO.java
package dao;

import model.DatabaseConnection;
import model.Menu;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {
    private Connection connection;

    public MenuDAO() {
        connection = DatabaseConnection.getConnection();
    }

    public List<Menu> getAllMenu() {
        List<Menu> menuList = new ArrayList<>();
        String query = "SELECT m.*, k.nama_kategori FROM menu m " +
                "JOIN kategori k ON m.id_kategori = k.id_kategori " +
                "WHERE m.stok > 0 ORDER BY k.nama_kategori, m.nama_menu";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Menu menu = new Menu(
                        rs.getInt("id_menu"),
                        rs.getString("nama_menu"),
                        rs.getString("nama_kategori"),
                        rs.getDouble("harga"),
                        rs.getInt("stok")
                );
                menuList.add(menu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuList;
    }

    public List<Menu> getMenuByKategori(String kategori) {
        List<Menu> menuList = new ArrayList<>();
        String query = "SELECT m.*, k.nama_kategori FROM menu m " +
                "JOIN kategori k ON m.id_kategori = k.id_kategori " +
                "WHERE k.nama_kategori = ? AND m.stok > 0 ORDER BY m.nama_menu";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, kategori);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Menu menu = new Menu(
                        rs.getInt("id_menu"),
                        rs.getString("nama_menu"),
                        rs.getString("nama_kategori"),
                        rs.getDouble("harga"),
                        rs.getInt("stok")
                );
                menuList.add(menu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuList;
    }

    public boolean updateStok(int idMenu, int jumlah) {
        String query = "UPDATE menu SET stok = stok - ? WHERE id_menu = ? AND stok >= ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, jumlah);
            pstmt.setInt(2, idMenu);
            pstmt.setInt(3, jumlah);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
