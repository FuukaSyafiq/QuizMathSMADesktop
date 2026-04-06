package com.tugas.quizmath_player.form;

import com.tugas.quizmath_player.entity.Leaderboard;
import com.tugas.quizmath_player.helper.Session;
import com.tugas.quizmath_player.repository.FinalScoreRepository;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class QuizResultForm extends JFrame {
    private int wrongAnswers;
    private int score;
    private final FinalScoreRepository scoreRepo;

    public QuizResultForm(String level, int totalQuestions, int correctAnswers) {
        this.wrongAnswers = totalQuestions - correctAnswers;
        this.score = (int) ((correctAnswers * 100.0) / totalQuestions);
        this.scoreRepo = new FinalScoreRepository();

        setTitle("Quiz Game - Hasil & Riwayat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Panel utama dengan background gambar
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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // Card 1: Hasil Sekarang (Kiri)
        JPanel resultCard = createResultCard(level, totalQuestions, correctAnswers);
        gbc.gridx = 0;
        gbc.weightx = 0.4;
        mainPanel.add(resultCard, gbc);

        // Card 2: Riwayat (Kanan)
        JPanel historyCard = createHistoryCard();
        gbc.gridx = 1;
        gbc.weightx = 0.6;
        mainPanel.add(historyCard, gbc);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createResultCard(String level, int totalQuestions, int correctAnswers) {
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBackground(Color.WHITE);
        resultPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(52, 73, 94), 4),
                new EmptyBorder(40, 50, 40, 50)));

        // Judul
        JLabel titleLabel = new JLabel("HASIL QUIZ");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(new Color(52, 73, 94));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Level
        JLabel levelLabel = new JLabel("Level: " + level);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 24));
        levelLabel.setForeground(new Color(52, 73, 94));
        levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        resultPanel.add(titleLabel);
        resultPanel.add(Box.createVerticalStrut(10));
        resultPanel.add(levelLabel);
        resultPanel.add(Box.createVerticalStrut(30));

        // Detail hasil
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(3, 2, 10, 15));
        detailsPanel.setOpaque(false);
        detailsPanel.setMaximumSize(new Dimension(400, 150));

        addDetailRow(detailsPanel, "Total Soal:", String.valueOf(totalQuestions), new Color(52, 73, 94));
        addDetailRow(detailsPanel, "Jawaban Benar:", String.valueOf(correctAnswers), new Color(46, 204, 113));
        addDetailRow(detailsPanel, "Jawaban Salah:", String.valueOf(wrongAnswers), new Color(231, 76, 60));

        resultPanel.add(detailsPanel);
        resultPanel.add(Box.createVerticalStrut(30));

        // Pesan motivasi
        JLabel messageLabel = new JLabel(getMotivationMessage(score));
        messageLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        messageLabel.setForeground(new Color(127, 140, 141));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultPanel.add(messageLabel);
        resultPanel.add(Box.createVerticalStrut(30));

        // Tombol aksi
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        
        JButton btnPlayAgain = createActionButton("MAIN LAGI", new Color(46, 204, 113), new Color(39, 174, 96));
        btnPlayAgain.addActionListener(e -> playAgain());

        JButton btnExit = createActionButton("KELUAR", new Color(231, 76, 60), new Color(192, 57, 43));
        btnExit.addActionListener(e -> {
            new LoginForm().setVisible(true);
            this.dispose();
        });

        buttonPanel.add(btnPlayAgain);
        buttonPanel.add(btnExit);
        resultPanel.add(buttonPanel);

        return resultPanel;
    }

    private void addDetailRow(JPanel panel, String label, String value, Color color) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        lbl.setForeground(new Color(52, 73, 94));
        
        JLabel val = new JLabel(value);
        val.setFont(new Font("Arial", Font.PLAIN, 18));
        val.setForeground(color);
        val.setHorizontalAlignment(SwingConstants.RIGHT);
        
        panel.add(lbl);
        panel.add(val);
    }

    private JPanel createHistoryCard() {
        JPanel historyPanel = new JPanel(new BorderLayout(10, 10));
        historyPanel.setBackground(Color.WHITE);
        historyPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(52, 73, 94), 4),
                new EmptyBorder(30, 30, 30, 30)));

        JLabel titleLabel = new JLabel("RIWAYAT NILAI KAMU");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(52, 73, 94));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        historyPanel.add(titleLabel, BorderLayout.NORTH);

        String[] columns = {"Tanggal", "Nilai", "Benar", "Salah", "Total"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(236, 240, 241));

        // Center align table contents
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Adjust column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(180); // Tanggal
        table.getColumnModel().getColumn(1).setPreferredWidth(60);  // Nilai

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        historyPanel.add(scrollPane, BorderLayout.CENTER);

        // Load data
        List<Leaderboard> history = scoreRepo.getScoreBySiswa(Session.getCurrentSiswaId());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        for (Leaderboard l : history) {
            model.addRow(new Object[]{
                sdf.format(l.createdAt),
                l.final_score,
                l.correct_anwer,
                l.wrong_anwer,
                l.total_question
            });
        }

        return historyPanel;
    }

    private String getMotivationMessage(int score) {
        if (score >= 90) return "Sempurna! Kamu luar biasa!";
        else if (score >= 80) return "Bagus sekali! Pertahankan!";
        else if (score >= 70) return "Lumayan! Masih bisa lebih baik!";
        else if (score >= 60) return "Cukup baik, terus berlatih!";
        else return "Jangan menyerah, coba lagi!";
    }

    private JButton createActionButton(String text, Color color1, Color color2) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2d.setColor(color2);
                } else if (getModel().isRollover()) {
                    GradientPaint gp = new GradientPaint(0, 0, color1.brighter(), 0, getHeight(), color2.brighter());
                    g2d.setPaint(gp);
                } else {
                    GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                    g2d.setPaint(gp);
                }

                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2d.drawString(getText(), x, y);
            }
        };

        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(180, 50));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void playAgain() {
        this.dispose();
        SwingUtilities.invokeLater(() -> new QuizLevelSelection());
    }
}
