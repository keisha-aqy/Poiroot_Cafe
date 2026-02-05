// Pesanan.java
package model;

import java.util.Date;
import java.util.List;

public class Pesanan {
    private int idPesanan;
    private String nomorPesanan;
    private String namaPelanggan;
    private String jenisPesanan;
    private Integer idMeja;
    private String nomorMeja;
    private double totalHarga;
    private String metodeBayar;
    private String statusPesanan;
    private Date tanggalPesanan;
    private List<Menu> items;

    public Pesanan() {}

    // Getters and Setters
    public int getIdPesanan() { return idPesanan; }
    public void setIdPesanan(int idPesanan) { this.idPesanan = idPesanan; }

    public String getNomorPesanan() { return nomorPesanan; }
    public void setNomorPesanan(String nomorPesanan) { this.nomorPesanan = nomorPesanan; }

    public String getNamaPelanggan() { return namaPelanggan; }
    public void setNamaPelanggan(String namaPelanggan) { this.namaPelanggan = namaPelanggan; }

    public String getJenisPesanan() { return jenisPesanan; }
    public void setJenisPesanan(String jenisPesanan) { this.jenisPesanan = jenisPesanan; }

    public Integer getIdMeja() { return idMeja; }
    public void setIdMeja(Integer idMeja) { this.idMeja = idMeja; }

    public String getNomorMeja() { return nomorMeja; }
    public void setNomorMeja(String nomorMeja) { this.nomorMeja = nomorMeja; }

    public double getTotalHarga() { return totalHarga; }
    public void setTotalHarga(double totalHarga) { this.totalHarga = totalHarga; }

    public String getMetodeBayar() { return metodeBayar; }
    public void setMetodeBayar(String metodeBayar) { this.metodeBayar = metodeBayar; }

    public String getStatusPesanan() { return statusPesanan; }
    public void setStatusPesanan(String statusPesanan) { this.statusPesanan = statusPesanan; }

    public Date getTanggalPesanan() { return tanggalPesanan; }
    public void setTanggalPesanan(Date tanggalPesanan) { this.tanggalPesanan = tanggalPesanan; }

    public List<Menu> getItems() { return items; }
    public void setItems(List<Menu> items) { this.items = items; }
}
