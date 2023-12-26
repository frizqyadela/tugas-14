public class Barang {
    private String namaBarang;
    private double hargaBarang;
    private int jumlahBarang;

    public Barang(String namaBarang, double hargaBarang, int jumlahBarang) {
        this.namaBarang = namaBarang;

        this.hargaBarang = hargaBarang;
        this.jumlahBarang = jumlahBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public double getHargaBarang() {
        return hargaBarang;
    }

    public int getJumlahBarang() {
        return jumlahBarang;
    }

    // Method to calculate total harga
    public double getTotalHarga() {
        return hargaBarang * jumlahBarang;
    }
}