public class TransaksiPenjualan extends Barang implements Transaksi {
    public static final String getTotalHarga = null;
    private String noFaktur;
    private String namaPelanggan;
    private String alamatPelanggan;
    private String nomorHP;
    private String kodeBarang;
    private double totalBayar;

    // Constructor
    public TransaksiPenjualan(String noFaktur, String namaPelanggan, String alamatPelanggan, String nomorHP, String kodeBarang, String namaBarang, double hargaBarang, int jumlahBarang) {
        super(namaBarang, hargaBarang, jumlahBarang);
        this.noFaktur = noFaktur;
        this.namaPelanggan = namaPelanggan;
        this.alamatPelanggan = alamatPelanggan;
    }

    // Override interface methods
    @Override
    public void hitungTotalBayar() {
        totalBayar = getTotalHarga();
    }

    @Override
    public void tampilDetail() {
       
    }

    public String getTotalBayar() {
        return null;
    }
}