// Meja.java
package model;

public class Meja {
    private int idMeja;
    private String nomorMeja;
    private int kapasitas;
    private String status;

    public Meja() {}

    public Meja(int idMeja, String nomorMeja, int kapasitas, String status) {
        this.idMeja = idMeja;
        this.nomorMeja = nomorMeja;
        this.kapasitas = kapasitas;
        this.status = status;
    }

    // Getters and Setters
    public int getIdMeja() { return idMeja; }
    public void setIdMeja(int idMeja) { this.idMeja = idMeja; }

    public String getNomorMeja() { return nomorMeja; }
    public void setNomorMeja(String nomorMeja) { this.nomorMeja = nomorMeja; }

    public int getKapasitas() { return kapasitas; }
    public void setKapasitas(int kapasitas) { this.kapasitas = kapasitas; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}