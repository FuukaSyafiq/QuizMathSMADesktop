package com.tugas.quizmath_player.form;

import com.tugas.quizmath_player.entity.Kelas;
import com.tugas.quizmath_player.repository.KelasRepository;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

public class JurusanForm extends JPanel {

    private KelasRepository kelasRepository;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtKelas;
    private JComboBox<String> cbJurusan;
    private JButton btnTambah;
    private JButton btnEdit;
    private JButton btnHapus;
    private JButton btnRefresh;

    public JurusanForm() {
        this.kelasRepository = new KelasRepository();
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel title = new JLabel("Data Kelas & Jurusan");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Top Panel: Form
        JPanel formPanel = new JPanel(new BorderLayout(10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Kelas & Jurusan"));
        formPanel.setBackground(Color.WHITE);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        inputPanel.setOpaque(false);

        inputPanel.add(new JLabel("Kelas:"));
        txtKelas = new JTextField(10);
        inputPanel.add(txtKelas);

        inputPanel.add(new JLabel("Jurusan:"));
        String[] jurusanList = {
            "REKAYASA PERANGKAT LUNAK",
            "AKUNTANSI",
            "PERBANKAN",
            "DESAIN KOMUNIKASI VISUAL",
            "TEKNIK KOMPUTER JARINGAN",
            "MEKATRONIKA"
        };
        cbJurusan = new JComboBox<>(jurusanList);
        inputPanel.add(cbJurusan);

        btnTambah = new JButton("Tambah");
        btnTambah.setBackground(new Color(0, 123, 255));
        btnTambah.setForeground(Color.WHITE);
        btnTambah.addActionListener(e -> tambahKelas());
        inputPanel.add(btnTambah);

        btnEdit = new JButton("Edit");
        btnEdit.setBackground(new Color(255, 193, 7));
        btnEdit.setForeground(Color.BLACK);
        btnEdit.addActionListener(e -> editKelas());
        inputPanel.add(btnEdit);

        btnHapus = new JButton("Hapus");
        btnHapus.setBackground(new Color(220, 53, 69));
        btnHapus.setForeground(Color.WHITE);
        btnHapus.addActionListener(e -> hapusKelas());
        inputPanel.add(btnHapus);

        btnRefresh = new JButton("Refresh");
        btnRefresh.setBackground(new Color(40, 167, 69));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.addActionListener(e -> loadData());
        inputPanel.add(btnRefresh);

        formPanel.add(inputPanel, BorderLayout.NORTH);

        // Table
        String[] columnNames = { "ID", "Kelas", "Jurusan" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(0, 123, 255));
        table.getTableHeader().setForeground(Color.WHITE);

        // Hide ID column
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);

        // Add selection listener
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedData();
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        formPanel.add(scrollPane, BorderLayout.CENTER);

        add(formPanel, BorderLayout.CENTER);
    }

    private void loadData() {
        new SwingWorker<List<Kelas>, Void>() {
            @Override
            protected List<Kelas> doInBackground() {
                return kelasRepository.getAllKelas(JurusanForm.this);
            }

            @Override
            protected void done() {
                try {
                    List<Kelas> list = get();
                    populateTable(list);
                    clearForm();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(JurusanForm.this, "Error: " + e.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    private void populateTable(List<Kelas> list) {
        tableModel.setRowCount(0);
        for (Kelas k : list) {
            Object[] row = { k.getId(), k.getKelas(), k.getJurusan() };
            tableModel.addRow(row);
        }
    }

    private void loadSelectedData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            txtKelas.setText((String) table.getValueAt(selectedRow, 1));
            String jurusanVal = (String) table.getValueAt(selectedRow, 2);
            cbJurusan.setSelectedItem(jurusanVal);
        }
    }

    private void clearForm() {
        txtKelas.setText("");
        cbJurusan.setSelectedIndex(0);
        table.clearSelection();
    }

    private void tambahKelas() {
        String kelas = txtKelas.getText().trim();
        String jurusan = (String) cbJurusan.getSelectedItem();
        if (kelas.isEmpty() || jurusan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kelas dan Jurusan tidak boleh kosong", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 1).toString().equalsIgnoreCase(kelas)) {
                JOptionPane.showMessageDialog(this, "Kelas " + kelas + " sudah ada", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                kelasRepository.insertKelas(kelas, jurusan, JurusanForm.this);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    JOptionPane.showMessageDialog(JurusanForm.this, "Data Kelas & Jurusan berhasil ditambahkan", "Sukses",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadData();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(JurusanForm.this, "Error: " + e.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    private void editKelas() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diedit", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        String kelas = txtKelas.getText().trim();
        String jurusan = (String) cbJurusan.getSelectedItem();
        
        if (kelas.isEmpty() || jurusan == null || jurusan.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kelas dan Jurusan tidak boleh kosong", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int rowId = (int) tableModel.getValueAt(i, 0);
            if (tableModel.getValueAt(i, 1).toString().equalsIgnoreCase(kelas) && rowId != id) {
                JOptionPane.showMessageDialog(this, "Kelas " + kelas + " sudah ada", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                kelasRepository.updateKelas(id, kelas, jurusan, JurusanForm.this);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    JOptionPane.showMessageDialog(JurusanForm.this, "Data berhasil diupdate", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    loadData();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(JurusanForm.this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    private void hapusKelas() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        String kelas = (String) table.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus kelas " + kelas + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                kelasRepository.deleteKelas(id, JurusanForm.this);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    JOptionPane.showMessageDialog(JurusanForm.this, "Data berhasil dihapus", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    loadData();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(JurusanForm.this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }
}
