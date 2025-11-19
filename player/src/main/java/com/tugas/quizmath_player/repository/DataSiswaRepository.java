/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tugas.quizmath_player.repository;
import com.tugas.quizmath_player.entity.Siswa;
import database.Database;
import entity.DataSiswa;
import entity.Kelas;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import helper.Session;
import java.awt.Component;
import javax.swing.JOptionPane;
/**
 *
 * @author syafiq
 */
public class DataSiswaRepository {
    
   public void loginSiswa(DataSiswa dataSiswa) {
    String sqlInsert = "INSERT INTO siswa(nama, kelas_id, nis, no_absen) VALUES (?, ?, ?, ?)";

    try (Connection conn = Database.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sqlInsert,Statement.RETURN_GENERATED_KEYS)) {

        stmt.setString(1, dataSiswa.nama);
        stmt.setInt(2, dataSiswa.kelas_id);
        stmt.setString(3, dataSiswa.nis);
        stmt.setInt(4, Integer.parseInt(dataSiswa.noAbsen));

        int rows = stmt.executeUpdate();
        if (rows > 0) {
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int siswaId = rs.getInt(1); // ambil id auto_increment
                    System.out.println("✅ Data siswa berhasil ditambahkan dengan ID: " + siswaId);

                    // langsung set session
                    Session.setSession(siswaId, dataSiswa.nama);
                }
            }
        } else {
            System.out.println("⚠️ Tidak ada data yang ditambahkan");
        }

    } catch (SQLException e) {
        System.err.println("❌ Gagal insert siswa: " + e.getMessage());
    }
}

   
  public List<Kelas> getAllKelas() {
    List<Kelas> listKelas = new ArrayList<>();
    String sql = "SELECT * FROM kelas";

     try (Connection conn = Database.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            int id = rs.getInt("id");
            String kelas = rs.getString("kelas");
            String jurusan = rs.getString("jurusan");

            Kelas k = new Kelas(id, kelas, jurusan);
            listKelas.add(k);
        }

    } catch (SQLException e) {
        System.err.println("❌ Gagal ambil data kelas: " + e.getMessage());
    }

    return listKelas;
}

public List<Siswa> getAllSiswa(Component parentComponent) {
        List<Siswa> result = new ArrayList<>();
        String sql = """
            SELECT s.id, s.nama, s.no_absen, s.nis, k.kelas,k.jurusan
            FROM siswa s
            INNER JOIN kelas k ON s.kelas_id = k.id
        """;

       try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nama = rs.getString("nama");
                String kelas = rs.getString("kelas");
                String jurusan = rs.getString("jurusan");
                String nis = rs.getString("nis");
                int noAbsen = rs.getInt("no_absen");
                
               Siswa siswa = new Siswa(id,nama,nis,noAbsen,kelas,jurusan);
               result.add(siswa);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                parentComponent,
                "Terjadi error saat mengambil data siswa",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }

        return result;
    }
   
  public void deleteSiswa(int selectedId, Component parentComponent) {
    String deleteFinalScore = "DELETE FROM final_score WHERE siswa_id = ?";
    String deleteSiswa = "DELETE FROM siswa WHERE id = ?";

    try (Connection conn = Database.getConnection()) {
        conn.setAutoCommit(false); // mulai transaksi

        // 1. Hapus final_score siswa
        try (PreparedStatement stmtFinal = conn.prepareStatement(deleteFinalScore)) {
            stmtFinal.setInt(1, selectedId);
            stmtFinal.executeUpdate();
        }

        // 2. Hapus data siswa
        try (PreparedStatement stmtSiswa = conn.prepareStatement(deleteSiswa)) {
            stmtSiswa.setInt(1, selectedId);
            stmtSiswa.executeUpdate();
        }

        conn.commit();

        JOptionPane.showMessageDialog(
            parentComponent,
            "Data siswa dan skor final berhasil dihapus.",
            "Sukses",
            JOptionPane.INFORMATION_MESSAGE
        );

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
            parentComponent,
            "Terjadi error saat menghapus data siswa: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
        e.printStackTrace();
    }
}

   
   public void updateSiswa(Siswa siswa, int kelas_id,Component parentComponent) {
    String sql = """
        UPDATE siswa 
        SET nama = ?, no_absen = ?, nis = ?, kelas_id = ? 
        WHERE id = ?
    """;

    try (Connection conn = Database.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, siswa.nama);
        stmt.setInt(2, siswa.no_absen);
        stmt.setString(3, siswa.nis);
        stmt.setInt(4, kelas_id); // pastikan ada field kelas_id di model Siswa
        stmt.setInt(5, siswa.id);

        stmt.executeUpdate();

        JOptionPane.showMessageDialog(
            parentComponent,
            "Data siswa berhasil diedit.",
            "Sukses",
            JOptionPane.INFORMATION_MESSAGE
        );
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                parentComponent,
                "Terjadi error saat mengedit data siswa",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        e.printStackTrace();
    }
}
}
