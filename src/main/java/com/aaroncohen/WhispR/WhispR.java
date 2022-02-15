package com.aaroncohen.WhispR;

import com.formdev.flatlaf.*;
import com.google.gson.Gson;
import net.coobird.thumbnailator.Thumbnails;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Code written by Aaron Cohen
 * File Created On: 02/10/2022
 */

public class WhispR extends JFrame implements FileLoader {

    private JPanel defaultPanel;
    private JLabel titleLabel;
    private JLabel authorLabel;
    private JPanel contentPanel;
    private JLabel logoLabel;
    private JButton settingsButton;

    public static final Color ERROR_RED = new Color(255, 71, 71);

    public static Settings settings;
    private static SplashScreen splashScreen;

    public WhispR() {
        setContentPane(defaultPanel);
        setTitle("WhispR");
        setSize(settings.size);
        setMinimumSize(new Dimension(574, 435));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //set the position of the window from memory, or to the center of the screen
        if (settings.posx == -1 && settings.posy == -1) setLocationRelativeTo(null);
        else setLocation(settings.posx, settings.posy);
        //save frame data when the window closes
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    settings.size = getSize();
                    settings.posx = getX();
                    settings.posy = getY();
                    settings.save();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        //set up components here
        DefaultPanel defaultPanel = new DefaultPanel(contentPanel);
        contentPanel.add(defaultPanel.getPanel());

        //scale images
        try {
            //add logo
            logoLabel.setIcon(new ImageIcon(Thumbnails.of(getFileFromResourceAsStream(
                    "logo_" + (settings.theme == Settings.Theme.Dark ? "white" : "black") + ".png"))
                    .size(40, 40).asBufferedImage()));
            //settings button
            settingsButton.setIcon(new ImageIcon(Thumbnails.of(getFileFromResourceAsStream(
                    "settings_" + (settings.theme == Settings.Theme.Dark ? "white" : "black") + ".png"))
                    .size(25, 25).asBufferedImage()));
        } catch (Exception e) {
            e.printStackTrace();
        }    //there should be no exception thrown

        settingsButton.setContentAreaFilled(false);
        settingsButton.setBorderPainted(false);
        settingsButton.addActionListener((e -> {
            ChatNetworker.running = false;
            JPanel panelToRemove = (JPanel) contentPanel.getComponent(0);
            contentPanel.add(new SettingsPanel(contentPanel).getPanel());
            contentPanel.remove(panelToRemove);
        }));

        //remove splash screen and load in window
        splashScreen.setVisible(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        //mac cmd+q support
        System.setProperty("apple.eawt.quitStrategy", "CLOSE_ALL_WINDOWS");
        //load or create new settings
        boolean win = System.getProperty("os.name").startsWith("Windows");
        File settingsFile = new File(System.getProperty("user.home") +
                (win ? "/AppData/Roaming/WhispR" : "") + "/.WhispR");
        try {
            if (!settingsFile.exists()) {
                settings = new Settings();
                //write the settings file
                if (win) new File(System.getProperty("user.home") + "/AppData/Roaming/WhispR").mkdir();
                settingsFile.createNewFile();
                //write settings output to file
                settings.save();
            } else {
                //load settings if it exists
                Gson gson = new Gson();
                settings = gson.fromJson(new FileReader(settingsFile), Settings.class);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Couldn't load settings", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        //if settings exists but is empty (incorrect file permissions or other errors)
        if (settings == null) {
            JOptionPane.showMessageDialog(null, "Error: Couldn't load settings\n", "Error", JOptionPane.ERROR_MESSAGE);
            settings = new Settings();
        }
        //set up FlatLaf
        if (settings.theme == Settings.Theme.Dark) FlatDarculaLaf.setup(); else FlatLightLaf.setup();
        //load Splash Screen
        splashScreen = new SplashScreen();
        //create the window
        WhispR whispR = new WhispR();
        //set up custom styling for elements here
        whispR.titleLabel.putClientProperty("FlatLaf.styleClass", "h0");
        whispR.authorLabel.putClientProperty("FlatLaf.styleClass", "medium");
    }
}

