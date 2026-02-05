// Menu.java
package model;

public class Menu {
    private int idMenu;
    private String namaMenu;
    private int idKategori;
    private String kategori;
    private double harga;
    private int stok;
    private int jumlahPesan;

    public Menu() {}

    public Menu(int idMenu, String namaMenu, String kategori, double harga, int stok) {
        this.idMenu = idMenu;
        this.namaMenu = namaMenu;
        this.kategori = kategori;
        this.harga = harga;
        this.stok = stok;
        this.jumlahPesan = 0;
    }

    // Getters and Setters
    public int getIdMenu() { return idMenu; }
    public void setIdMenu(int idMenu) { this.idMenu = idMenu; }

    public String getNamaMenu() { return namaMenu; }
    public void setNamaMenu(String namaMenu) { this.namaMenu = namaMenu; }

    public int getIdKategori() { return idKategori; }
    public void setIdKategori(int idKategori) { this.idKategori = idKategori; }

    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }

    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }

    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }

    public int getJumlahPesan() { return jumlahPesan; }
    public void setJumlahPesan(int jumlahPesan) { this.jumlahPesan = jumlahPesan; }

    public double getSubtotal() {
        return harga * jumlahPesan;
    }
}