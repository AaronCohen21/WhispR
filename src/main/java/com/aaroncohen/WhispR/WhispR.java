package com.aaroncohen.WhispR;

import com.formdev.flatlaf.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.*;

/**
 * Code written by Aaron Cohen
 * File Created On: 02/10/2022
 */

public class WhispR extends JFrame {

    private JPanel defaultPanel;
    private JLabel titleLabel;
    private JLabel authorLabel;
    private JPanel contentPanel;

    public static final Color ERROR_RED = new Color(255, 71, 71);

    public WhispR() {
        setContentPane(defaultPanel);
        setTitle("WhispR");
        setSize(new Dimension(574, 435));
        setMinimumSize(getSize());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        //set up components here
        contentPanel.add(new DefaultPanel(contentPanel).getPanel());
    }

    public static void main(String[] args) throws InterruptedException {
        //set up FlatLaf
        FlatDarculaLaf.setup();
        //create the window
        WhispR whispR = new WhispR();
        //set up custom styling for elements here
        whispR.titleLabel.putClientProperty("FlatLaf.styleClass", "h0");
        whispR.authorLabel.putClientProperty("FlatLaf.styleClass", "medium");
    }
}

