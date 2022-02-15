package com.aaroncohen.WhispR;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.io.IOException;

import static com.aaroncohen.WhispR.WhispR.settings;

/**
 * Code written by Aaron Cohen
 * File Created On: 02/13/2022
 */

public class SplashScreen extends JFrame implements FileLoader {

    private JPanel panel;
    private JLabel splashIcon;

    public SplashScreen() {
        setContentPane(panel);
        setUndecorated(true);
        try {
            splashIcon.setIcon(new ImageIcon(ImageIO.read(getFileFromResourceAsStream(
                    "splash_" + (settings.theme == Settings.Theme.Dark ? "white" : "black") + ".png"))));
        } catch (Exception ignored) {}
        setMinimumSize(new Dimension(500,124));
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
