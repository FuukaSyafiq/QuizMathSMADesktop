package com.tugas.quizmath_player.form;

import com.tugas.quizmath_player.helper.Session;
import com.tugas.quizmath_player.repository.AdminRepository;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class SettingsForm extends JPanel {

    private AdminRepository adminRepo;
    private com.tugas.quizmath_player.repository.QuizSettingRepository settingRepo;
    private JPasswordField txtOldPassword, txtNewPassword, txtConfirmPassword;
    private javax.swing.JSpinner txtTimeLimit;
    private JButton btnSavePassword, btnCancelPassword, btnSaveTime;

    public SettingsForm() {
        this.adminRepo = new AdminRepository();
        this.settingRepo = new com.tugas.quizmath_player.repository.QuizSettingRepository();
        initComponents();
        loadSettings();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel title = new JLabel("Pengaturan");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel mainWrapper = new JPanel();
        mainWrapper.setLayout(new javax.swing.BoxLayout(mainWrapper, javax.swing.BoxLayout.Y_AXIS));
        mainWrapper.setOpaque(false);

        // =======================
        // Form Panel: Password
        // =======================
        JPanel passwordPanel = new JPanel(new GridBagLayout());
        passwordPanel.setBorder(BorderFactory.createTitledBorder("Ubah Password"));
        passwordPanel.setBackground(Color.WHITE);
        passwordPanel.setMaximumSize(new java.awt.Dimension(500, 300));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        // Current Username
        gbc.gridx = 0; gbc.gridy = 0;
        passwordPanel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1;
        JLabel lblUsername = new JLabel(Session.getCurrentAdminUsername());
        lblUsername.setFont(new Font("Arial", Font.BOLD, 14));
        passwordPanel.add(lblUsername, gbc);

        // Old Password
        gbc.gridx = 0; gbc.gridy = 1;
        passwordPanel.add(new JLabel("Password Lama:"), gbc);
        
        gbc.gridx = 1;
        txtOldPassword = new JPasswordField(20);
        passwordPanel.add(txtOldPassword, gbc);

        // New Password
        gbc.gridx = 0; gbc.gridy = 2;
        passwordPanel.add(new JLabel("Password Baru:"), gbc);
        
        gbc.gridx = 1;
        txtNewPassword = new JPasswordField(20);
        passwordPanel.add(txtNewPassword, gbc);

        // Confirm Password
        gbc.gridx = 0; gbc.gridy = 3;
        passwordPanel.add(new JLabel("Konfirmasi Password:"), gbc);
        
        gbc.gridx = 1;
        txtConfirmPassword = new JPasswordField(20);
        passwordPanel.add(txtConfirmPassword, gbc);

        // Buttons Password
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        
        JPanel btnPanelPass = new JPanel();
        btnPanelPass.setOpaque(false);
        
        btnSavePassword = new JButton("Simpan");
        btnSavePassword.setBackground(new Color(46, 204, 113));
        btnSavePassword.setForeground(Color.WHITE);
        btnSavePassword.setPreferredSize(new java.awt.Dimension(100, 35));
        btnSavePassword.addActionListener(e -> changePassword());

        btnCancelPassword = new JButton("Batal");
        btnCancelPassword.setPreferredSize(new java.awt.Dimension(100, 35));
        btnCancelPassword.addActionListener(e -> clearForm());
        
        btnPanelPass.add(btnSavePassword);
        btnPanelPass.add(btnCancelPassword);
        passwordPanel.add(btnPanelPass, gbc);
        
        // =======================
        // Form Panel: Waktu Ujian
        // =======================
        JPanel timePanel = new JPanel(new GridBagLayout());
        timePanel.setBorder(BorderFactory.createTitledBorder("Pengaturan Ujian"));
        timePanel.setBackground(Color.WHITE);
        timePanel.setMaximumSize(new java.awt.Dimension(500, 150));
        
        GridBagConstraints gbcTime = new GridBagConstraints();
        gbcTime.insets = new Insets(10, 10, 10, 10);
        gbcTime.fill = GridBagConstraints.HORIZONTAL;
        gbcTime.weightx = 1.0;
        
        gbcTime.gridx = 0; gbcTime.gridy = 0;
        timePanel.add(new JLabel("Total Waktu Ujian (Menit):"), gbcTime);
        
        gbcTime.gridx = 1;
        txtTimeLimit = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(10, 1, 300, 1));
        timePanel.add(txtTimeLimit, gbcTime);

        gbcTime.gridx = 0; gbcTime.gridy = 1;
        gbcTime.gridwidth = 2;
        
        JPanel btnPanelTime = new JPanel();
        btnPanelTime.setOpaque(false);
        
        btnSaveTime = new JButton("Simpan Waktu");
        btnSaveTime.setBackground(new Color(52, 152, 219));
        btnSaveTime.setForeground(Color.WHITE);
        btnSaveTime.setPreferredSize(new java.awt.Dimension(130, 35));
        btnSaveTime.addActionListener(e -> saveTimeLimit());
        
        btnPanelTime.add(btnSaveTime);
        timePanel.add(btnPanelTime, gbcTime);

        // Add to main wrapper
        mainWrapper.add(passwordPanel);
        mainWrapper.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 20)));
        mainWrapper.add(timePanel);

        // Center the wrapper
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        GridBagConstraints wrapperGbc = new GridBagConstraints();
        centerWrapper.add(mainWrapper, wrapperGbc);
        
        add(centerWrapper, BorderLayout.CENTER);
    }
    
    private void loadSettings() {
        int limit = settingRepo.getQuizTimeLimit();
        txtTimeLimit.setValue(limit);
    }

    private void saveTimeLimit() {
        int limit = (int) txtTimeLimit.getValue();
        settingRepo.saveOrUpdateQuizTimeLimit(limit);
        JOptionPane.showMessageDialog(this, "Waktu ujian berhasil disimpan (" + limit + " Menit).");
    }

    private boolean validateForm() {
        String oldPassword = new String(txtOldPassword.getPassword());
        String newPassword = new String(txtNewPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());
        
        if (oldPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password lama harus diisi.");
            return false;
        }
        
        if (newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password baru harus diisi.");
            return false;
        }
        
        if (newPassword.length() < 4) {
            JOptionPane.showMessageDialog(this, "Password baru minimal 4 karakter.");
            return false;
        }
        
        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Password baru dan konfirmasi tidak cocok.");
            return false;
        }
        
        // Verify old password
        com.tugas.quizmath_player.entity.Admin admin = new com.tugas.quizmath_player.entity.Admin(
            Session.getCurrentAdminUsername(),
            oldPassword
        );
        
        if (!adminRepo.checkLogin(admin)) {
            JOptionPane.showMessageDialog(this, "Password lama salah.");
            return false;
        }
        
        return true;
    }

    private void changePassword() {
        if (!validateForm()) return;
        
        String newPassword = new String(txtNewPassword.getPassword());
        adminRepo.updatePassword(Session.getCurrentAdminUsername(), newPassword, this);
        clearForm();
    }

    private void clearForm() {
        txtOldPassword.setText("");
        txtNewPassword.setText("");
        txtConfirmPassword.setText("");
    }
}
