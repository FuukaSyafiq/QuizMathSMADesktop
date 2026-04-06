package com.tugas.quizmath_player.form;

import javax.swing.*;

import com.tugas.quizmath_player.constant.Errors;
import com.tugas.quizmath_player.entity.Admin;
import com.tugas.quizmath_player.entity.DataSiswa;
import com.tugas.quizmath_player.helper.Session;
import com.tugas.quizmath_player.repository.AdminRepository;
import com.tugas.quizmath_player.repository.DataSiswaRepository;
import java.awt.*;
import java.awt.event.*;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton loginButton;
    private JButton exitButton;
    private AdminRepository admin_repo;
    private DataSiswaRepository datasiswa_repo;

    public LoginForm() {
        setTitle("Quiz Application - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);
        this.datasiswa_repo = new DataSiswaRepository();
        this.admin_repo = new AdminRepository();

        // Panel utama dengan image background
        JPanel mainPanel = new JPanel() {
            private Image backgroundImage;
            {
                try {
                    java.io.File file1 = new java.io.File("mathbackground.jpg");
                    java.io.File file2 = new java.io.File("../mathbackground.jpg");
                    
                    if (file1.exists()) {
                        backgroundImage = javax.imageio.ImageIO.read(file1);
                    } else if (file2.exists()) {
                        backgroundImage = javax.imageio.ImageIO.read(file2);
                    } else {
                        java.net.URL imgUrl = getClass().getResource("/mathbackground.jpg");
                        if (imgUrl != null) {
                            backgroundImage = javax.imageio.ImageIO.read(imgUrl);
                        } else {
                            System.err.println("Warning: mathbackground.jpg tidak ditemukan!");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        mainPanel.setLayout(new GridBagLayout());

        // Panel login form
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setPreferredSize(new Dimension(450, 650));
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(40, 50, 40, 50)));
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

        // Icon/Logo
        ImageIcon originalIcon = new ImageIcon("smkantartika2.jpg");
        Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(iconLabel);

        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JLabel smkLabel = new JLabel("For SMK ANTARTIKA 2 SIDOARJO");
        smkLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        smkLabel.setForeground(new Color(41, 128, 185));
        smkLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(smkLabel);

        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Title
        JLabel titleLabel = new JLabel("Fuse Math");
        titleLabel.setFont(new Font("Open Sans", Font.BOLD, 32));
        titleLabel.setForeground(new Color(52, 73, 94));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(titleLabel);

        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Subtitle
        JLabel subtitleLabel = new JLabel("Silakan login untuk melanjutkan");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(127, 140, 141));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(subtitleLabel);

        loginPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Username field
        JPanel usernameLabelPanel = new JPanel();
        usernameLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        usernameLabelPanel.setBackground(Color.WHITE);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        usernameLabel.setForeground(new Color(52, 73, 94));

        usernameLabelPanel.add(usernameLabel);
        loginPanel.add(usernameLabelPanel);

        loginPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        usernameField.setMaximumSize(new Dimension(350, 45));
        // usernameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        loginPanel.add(usernameField);

        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Password field
        JPanel passwordLabelPanel = new JPanel();
        passwordLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        passwordLabelPanel.setBackground(Color.WHITE);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passwordLabel.setForeground(new Color(52, 73, 94));

        passwordLabelPanel.add(passwordLabel);
        loginPanel.add(passwordLabelPanel);

        loginPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setMaximumSize(new Dimension(350, 45));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        loginPanel.add(passwordField);

        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Role field
        JPanel roleLabelPanel = new JPanel();
        roleLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        roleLabelPanel.setBackground(Color.WHITE);

        JLabel roleLabel = new JLabel("Role");
        roleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        roleLabel.setForeground(new Color(52, 73, 94));

        roleLabelPanel.add(roleLabel);
        loginPanel.add(roleLabelPanel);

        loginPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        String[] roles = { "Admin", "Siswa" };
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        roleComboBox.setMaximumSize(new Dimension(350, 45));
        roleComboBox.setBackground(Color.WHITE);
        roleComboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        loginPanel.add(roleComboBox);

        loginPanel.add(Box.createRigidArea(new Dimension(0, 35)));

        // Login button
        JPanel loginBtnPanel = new JPanel();
        loginBtnPanel.setLayout(new BoxLayout(loginBtnPanel, BoxLayout.X_AXIS));
        loginBtnPanel.setBackground(Color.WHITE);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(41, 128, 185));
        loginButton.setFocusPainted(false);
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        loginButton.setBorderPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(new Color(52, 152, 219));
            }

            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(new Color(41, 128, 185));
            }
        });
        loginButton.addActionListener(e -> handleLogin());
        loginBtnPanel.add(loginButton);
        loginPanel.add(loginBtnPanel);

        loginPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Exit button
        JPanel exitBtnPanel = new JPanel();
        exitBtnPanel.setLayout(new BoxLayout(exitBtnPanel, BoxLayout.X_AXIS));
        exitBtnPanel.setBackground(Color.WHITE);

        exitButton = new JButton("Keluar");
        exitButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        exitButton.setForeground(new Color(127, 140, 141));
        exitButton.setBackground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        exitButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        exitButton.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                exitButton.setBackground(new Color(245, 245, 245));
            }

            public void mouseExited(MouseEvent e) {
                exitButton.setBackground(Color.WHITE);
            }
        });
        exitButton.addActionListener(e -> System.exit(0));
        exitBtnPanel.add(exitButton);
        loginPanel.add(exitBtnPanel);

        mainPanel.add(loginPanel);
        add(mainPanel);

        // Enter key action
        getRootPane().setDefaultButton(loginButton);

        setVisible(true);
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Username dan password tidak boleh kosong!",
                    "Login Gagal",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (role.equals("Siswa")) {
            DataSiswa formValues = new DataSiswa(username, password);
            Errors result = formValues.validate();
            int confirmDialog = JOptionPane.showConfirmDialog(
                    null,
                    "Apakah data sudah benar ?",
                    "Konfirmasi data siswa",
                    JOptionPane.OK_CANCEL_OPTION);

            if (confirmDialog == JOptionPane.OK_OPTION) {
                if (result.isError) {
                    JOptionPane.showMessageDialog(null, result.message, "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                   
                    DataSiswa res = datasiswa_repo.loginSiswa(formValues, this.rootPane);
                    if (res != null) {
                        // QuizLevelSelection quiz_selection = new QuizLevelSelection();
                        // quiz_selection.setVisible(true);
                        // (String nama, String nis, String absen, String kelas, String jurusan)
                        new OpeningForm(res.name, res.nis, res.absen, res.kelas, res.jurusan);
                        this.dispose();

                    }
                    return;
                }
            }
        }
        if (role.equals("Admin")) {
            Admin admin = new Admin(username, password);
            Errors err = admin.validate();
            if (err.isError) {
                JOptionPane.showMessageDialog(this, err.message, err.title, JOptionPane.ERROR_MESSAGE);
                return;
            }

            // if (admin.username.equals("admin") && admin.password.equals("admin123")) {
            boolean checkLogin = this.admin_repo.checkLogin(admin);
            // boolean checkLogin = true;
            if (checkLogin) {
                Session.setAdminSession(username); // Set admin session

                try {
                    // Execute the admin module
                    new DashboardForm().setVisible(true);
                    this.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username atau password tidak ditemukan", "User not found",
                        JOptionPane.ERROR_MESSAGE);
            }
            // }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new LoginForm());
    }
}