/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tugas.quizmath_player.repository;

import com.tugas.quizmath_player.entity.Kelas;
import com.tugas.quizmath_player.database.Database;
import org.hibernate.Session;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author syafiq
 */
public class KelasRepository {
    public List<Kelas> getAllKelas(Component parentComponent) {
        try (Session session = Database.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Kelas> criteria = builder.createQuery(Kelas.class);
            criteria.from(Kelas.class);
            return session.createQuery(criteria).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentComponent, "Gagal mengambil data kelas: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    public void insertKelas(String namaKelas, String jurusan, Component parentComponent) {
        org.hibernate.Transaction transaction = null;
        try (Session session = Database.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Kelas kelas = new Kelas(namaKelas, jurusan);
            session.persist(kelas);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentComponent, "Gagal menambahkan kelas/jurusan: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateKelas(int id, String namaKelas, String jurusan, Component parentComponent) {
        org.hibernate.Transaction transaction = null;
        try (Session session = Database.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Kelas kelas = session.get(Kelas.class, id);
            if (kelas != null) {
                kelas.setKelas(namaKelas);
                kelas.setJurusan(jurusan);
                session.merge(kelas);
                transaction.commit();
            } else {
                JOptionPane.showMessageDialog(parentComponent, "Data tidak ditemukan!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentComponent, "Gagal mengupdate kelas/jurusan: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteKelas(int id, Component parentComponent) {
        org.hibernate.Transaction transaction = null;
        try (Session session = Database.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Kelas kelas = session.get(Kelas.class, id);
            if (kelas != null) {
                session.remove(kelas);
                transaction.commit();
            } else {
                JOptionPane.showMessageDialog(parentComponent, "Data tidak ditemukan!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentComponent, "Gagal menghapus kelas/jurusan: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
