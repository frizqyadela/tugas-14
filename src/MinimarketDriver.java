import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MinimarketDriver {
    //Code untuk menyambungkan ke SQL
    public String driver = "com.mysql.jdbc.Driver";
    public String url = "jdbc:mysql://localhost:3306/jdbc";
    public static String uname = "root";
    public static String pass = "";

    public static boolean validasiLogin(String enteredUsername, String enteredPassword) {
       
        String validUsername = "Haidefa";
        String validPassword = "df7777";

        return validUsername.equals(enteredUsername) && validPassword.equals(enteredPassword);
    }

    public static String generateCaptcha() {
        
        return "ABCD0325"; 
    }

    //Method untuk menambahkan barang ke database
    public void tambahBarang(String namaBarang, double hargaBarang){
        try (Connection connection = DriverManager.getConnection(url, uname, pass);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO barang (nama, harga) VALUES (?, ?)")) {
            statement.setString(1, namaBarang);
            statement.setDouble(2, hargaBarang);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Barang berhasil ditambahkan!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Method untuk menampilkan daftar barang dari database
    public void lihatBarang() {
        try (Connection connection = DriverManager.getConnection(url, uname, pass);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM barang")) {
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id_barang") +
                        ", Nama: " + resultSet.getString("nama") +
                        ", Harga: " + resultSet.getDouble("harga"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Method untuk memperbaharui harga barang berdasarkan ID
    public void updateHargaBarang(int id, double hargaBaru) {
        try (Connection connection = DriverManager.getConnection(url, uname, pass);
             PreparedStatement statement = connection.prepareStatement("UPDATE barang SET harga = ? WHERE id_barang = ?")) {
            statement.setDouble(1, hargaBaru);
            statement.setInt(2, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Harga barang berhasil diupdate!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Method untuk menghapus barang berdasarkan ID
    public void hapusBarang(int id) {
        try (Connection connection = DriverManager.getConnection(url, uname, pass);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM barang WHERE id_barang = ?")) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Barang berhasil dihapus!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MinimarketDriver manager = new MinimarketDriver();

        boolean isValidLogin = false;
        boolean isValidCaptcha = false;

        do {
            // Proses login
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            // Validasi login
            isValidLogin = validasiLogin(username, password);

            if (!isValidLogin) {
                System.out.println("Login gagal. Silakan coba lagi.");
            }
        } while (!isValidLogin);

        do {
            // Generate CAPTCHA
            String expectedCaptcha = generateCaptcha();
            System.out.println("CAPTCHA: " + expectedCaptcha);

            System.out.print("Masukkan CAPTCHA: ");
            String enteredCaptcha = scanner.nextLine();

            // Validasi CAPTCHA dengan method string equalsIgnoreCase
            isValidCaptcha = expectedCaptcha.equalsIgnoreCase(enteredCaptcha);

            if (!isValidCaptcha) {
                System.out.println("CAPTCHA salah. Silakan coba lagi.");
            }
        } while (!isValidCaptcha);

            System.out.println("");
            System.out.println("LOGIN BERHASIL");
            System.out.println("");
            System.out.println("Masukkan Data");

        try {
            System.out.print("No. Faktur        : ");
            String noFaktur = scanner.nextLine();

            System.out.print("Nama Pelanggan    : ");
            String namaPelanggan = scanner.nextLine();

            System.out.print("No. HP            : ");
            String nomorHP = scanner.nextLine();

            System.out.print("Alamat            : ");
            String alamatPelanggan = scanner.nextLine();

            System.out.print("Kode Barang       : ");
            String kodeBarang = scanner.nextLine();

            System.out.print("Nama Barang       : ");
            String namaBarang = scanner.nextLine();


            double hargaBarang;
            while (true) {
                try {
                    System.out.print("Harga Barang      : ");
                    hargaBarang = scanner.nextDouble();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Input harga tidak valid. Mohon masukkan angka.");
                    scanner.next(); 
                }
            }

            int jumlahBarang;
            while (true) {
                try {
                    System.out.print("Jumlah Barang     : ");
                    jumlahBarang = scanner.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Input jumlah tidak valid. Mohon masukkan angka.");
                    scanner.next(); 
                }
            }

            // Buat objek transaksi
            TransaksiPenjualan transaksi = new TransaksiPenjualan(noFaktur, namaPelanggan, alamatPelanggan, nomorHP, kodeBarang, namaBarang, hargaBarang, jumlahBarang);

            // Hitung total bayar
            transaksi.hitungTotalBayar();

            // Tampilkan detail transaksi
            transaksi.tampilDetail();

            // Tanggal dan Waktu
            Date date = new Date();
            SimpleDateFormat hari = new SimpleDateFormat("'Hari/Tanggal \t:' EEEEEEEEEE dd-MM-yyyy");
            SimpleDateFormat jam = new SimpleDateFormat("'Waktu \t\t:' hh:mm:ss z");

            // Tampilkan Struk
            System.out.println(" ");
            System.out.println(" ");
            System.out.println("============== DEFA MART ===============");
            System.out.println(hari.format(date));
            System.out.println(jam.format(date));
            System.out.println("No Faktur \t: " + noFaktur);
            System.out.println("========================================");
            System.out.println(" ");
            System.out.println("------------ DATA PELANGGAN ------------");
            System.out.println("Nama Pelanggan \t: " + namaPelanggan);
            System.out.println("No. HP \t\t: " + nomorHP);
            System.out.println("Alamat \t\t: " + alamatPelanggan);
            System.out.println(" ");
            System.out.println("-------- DATA PEMBELIAN BARANG ---------");
            System.out.println("Kode Barang \t: " + kodeBarang);
            System.out.println("Nama Barang \t: " + namaBarang);
            System.out.println("Harga \t\t: " + hargaBarang);
            System.out.println("Jumlah \t\t: " + jumlahBarang);
            System.out.println("Total Bayar \t: " + transaksi.getTotalHarga());
            System.out.println("========================================");
            System.out.println("Kasir \t\t: Frizqya Dela Pratiwi \n");
            System.out.println("");
            System.out.println("\t\t TERIMA KASIH \t\t");
            System.out.println("");

            // Method string
            System.out.println("toUpperCase\t: " + namaPelanggan.toUpperCase());
            System.out.println("length\t\t: " + namaPelanggan.length());
            System.out.println("equals\t\t: " + hari.equals(hari));

        int choice;

        do {
            //Membuat pilihan menu CRUD
            System.out.println("");
            System.out.println("===== PILIH MENU =====");
            System.out.println("0. Exit");
            System.out.println("1. Create Data Barang");
            System.out.println("2. Read Data Barang");
            System.out.println("3. Update Data Barang");
            System.out.println("4. Delete Data Barang");
            System.out.println("");

            System.out.print("Masukkan pilihan\n: ");
            System.out.println("");

            choice = scanner.nextInt();

            //Contoh penggunaan CRUD untuk barang
            switch (choice) {
                case 1:
                    manager.tambahBarang(namaBarang, hargaBarang);  //Create
                    break;
                case 2:
                    manager.lihatBarang();  //Read
                    break;
                case 3:
                    System.out.print("Masukkan ID barang untuk di update \n: ");
                    int idToUpdate = scanner.nextInt();
                    System.out.print("Masukkan harga barang\n: ");
                    double newPrice = scanner.nextInt();
                    manager.updateHargaBarang(idToUpdate, newPrice);    //Update (Harga)
                    break;
                case 4:
                    System.out.print("Masukkan ID barang untuk di hapus\n: ");
                    int idToDelete = scanner.nextInt();
                    manager.hapusBarang(idToDelete);    //Delete (Barang)
                    break;
                case 0:
                    System.out.print("Keluar");
                    break;
                default:
                    System.out.print("Pilihan tidak valid!");
                    break;
            }
        } while (choice !=0);

        } finally {
            scanner.close();
        }
    scanner.close();
    }   
  } 