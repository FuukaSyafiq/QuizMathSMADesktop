package com.tugas.quizmath_player.helper;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image image;
    private boolean stretch;

    public BackgroundPanel(Image img, boolean stretch, int opacity) {
        this.image = img;
        this.stretch = stretch;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (stretch) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.drawImage(image, 0, 0, this);
        }
    }
}
