// PesananDAO.java
package dao;

import model.DatabaseConnection;
import model.Pesanan;
import model.Menu;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PesananDAO {
    private Connection connection;

    public PesananDAO() {
        connection = DatabaseConnection.getConnection();
    }

    public String generateNomorPesanan() {
        String prefix = "POI";
        String datePart = new java.text.SimpleDateFormat("yyMMdd").format(new java.util.Date());
        String query = "SELECT COUNT(*) as count FROM pesanan WHERE DATE(tanggal_pesanan) = CURDATE()";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                int count = rs.getInt("count") + 1;
                return prefix + datePart + String.format("%03d", count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prefix + datePart + "001";
    }

    public int createPesanan(Pesanan pesanan) {
        String query = "INSERT INTO pesanan (nomor_pesanan, nama_pelanggan, jenis_pesanan, " +
                "id_meja, total_harga, metode_bayar, status_pesanan) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, pesanan.getNomorPesanan());
            pstmt.setString(2, pesanan.getNamaPelanggan());
            pstmt.setString(3, pesanan.getJenisPesanan());

            if (pesanan.getIdMeja() != null && pesanan.getIdMeja() > 0) {
                pstmt.setInt(4, pesanan.getIdMeja());
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }

            pstmt.setDouble(5, pesanan.getTotalHarga());
            pstmt.setString(6, pesanan.getMetodeBayar());
            pstmt.setString(7, pesanan.getStatusPesanan());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean createDetailPesanan(int idPesanan, List<Menu> items) {
        String query = "INSERT INTO detail_pesanan (id_pesanan, id_menu, jumlah, harga_satuan, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            for (Menu item : items) {
                if (item.getJumlahPesan() > 0) {
                    pstmt.setInt(1, idPesanan);
                    pstmt.setInt(2, item.getIdMenu());
                    pstmt.setInt(3, item.getJumlahPesan());
                    pstmt.setDouble(4, item.getHarga());
                    pstmt.setDouble(5, item.getSubtotal());
                    pstmt.addBatch();

                    // Update stok
                    MenuDAO menuDAO = new MenuDAO();
                    menuDAO.updateStok(item.getIdMenu(), item.getJumlahPesan());
                }
            }
            pstmt.executeBatch();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}