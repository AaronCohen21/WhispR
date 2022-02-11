package com.aaroncohen.ChattR;

import com.formdev.flatlaf.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.*;

/**
 * Code written by Aaron Cohen
 * File Created On: 02/10/2022
 */

public class ChattR extends JFrame {

    private JPanel defaultPanel;
    private JLabel titleLabel;
    private JLabel authorLabel;
    private JPanel contentPanel;

    public static final Color ERROR_RED = new Color(255, 71, 71);

    public ChattR() {
        setContentPane(defaultPanel);
        setTitle("ChattR");
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
        ChattR chattR = new ChattR();
        //set up custom styling for elements here
        chattR.titleLabel.putClientProperty("FlatLaf.styleClass", "h0");
        chattR.authorLabel.putClientProperty("FlatLaf.styleClass", "medium");
    }

    /**
     * From the Apache camel project
     *
     * Checks to see if a specific port is available.
     *
     * @param port the port to check for availability
     */
    public static boolean portAvaliable(int port) throws IllegalArgumentException{

        if (port < 4096 || port > 65535) {
            throw new IllegalArgumentException("Invalid start port: " + port);
        }

        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;
        } catch (IOException e) {
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    /* should not be thrown */
                }
            }
        }

        return false;
    }
}

