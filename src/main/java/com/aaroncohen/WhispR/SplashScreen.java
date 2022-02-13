package com.aaroncohen.WhispR;

import javax.swing.*;

import java.awt.*;

import static com.aaroncohen.WhispR.WhispR.settings;

/**
 * Code written by Aaron Cohen
 * File Created On: 02/13/2022
 */

public class SplashScreen extends JFrame {

    private JPanel panel;
    private JLabel splashIcon;

    public SplashScreen() {
        setContentPane(panel);
        setUndecorated(true);
        splashIcon.setIcon(new ImageIcon("src/res/splash_" + (settings.theme == Settings.Theme.Dark ? "white" : "black") + ".png"));
        setMinimumSize(new Dimension(500,124));
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
