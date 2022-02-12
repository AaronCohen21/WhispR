package com.aaroncohen.WhispR;

import com.apple.eawt.Application;
import com.formdev.flatlaf.*;
import com.google.gson.Gson;
import javax.swing.*;
import java.awt.*;
import java.awt.desktop.QuitEvent;
import java.awt.desktop.QuitHandler;
import java.awt.desktop.QuitResponse;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Code written by Aaron Cohen
 * File Created On: 02/10/2022
 */

public class WhispR extends JFrame {

    private JPanel defaultPanel;
    private JLabel titleLabel;
    private JLabel authorLabel;
    private JPanel contentPanel;
    private JPanel settingsContainer;
    private JButton settingsButton;

    public static final Color ERROR_RED = new Color(255, 71, 71);

    public static Settings settings;

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
        //mac cmd+q callback
        Application a = Application.getApplication();
        a.setQuitHandler(new QuitHandler() {
            @Override
            public void handleQuitRequestWith(QuitEvent e, QuitResponse response) {
                try {
                    settings.size = getSize();
                    settings.posx = getX();
                    settings.posy = getY();
                    settings.save();
                    System.exit(0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        //the window is ready now
        setVisible(true);
        //set up components here
        settingsButton = new JButton(getScaledImage(new ImageIcon("src/res/settings_" + (settings.theme == Settings.Theme.Dark ? "white" : "black") + ".png"), 25, 25));
        settingsContainer.add(settingsButton);
        DefaultPanel defaultPanel = new DefaultPanel(contentPanel);
        contentPanel.add(defaultPanel.getPanel());

        //when the settings button is clicked
        settingsButton.addActionListener((e -> {
            ChatNetworker.running = false;
            JPanel panelToRemove = (JPanel) contentPanel.getComponent(0);
            contentPanel.add(new SettingsPanel(contentPanel).getPanel());
            contentPanel.remove(panelToRemove);
        }));
    }

    public static void main(String[] args) {
        //load or create new settings
        File settingsFile = new File(System.getProperty("user.home") + "/.WhispR");
        try {
            if (!settingsFile.exists()) {
                settings = new Settings();
                //write the settings file
                settingsFile.createNewFile();
                //if the file is on a Windows system the next line will hide it, otherwise it will be hidden by default
                try {Files.setAttribute(Paths.get(settingsFile.getPath()), "dos:hidden", true);} catch (Exception ex) {}
                //write settings output to file
                settings.save();
            } else {
                //load settings if it exists
                Gson gson = new Gson();
                settings = (Settings) gson.fromJson(new FileReader(settingsFile), Settings.class);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Couldn't load settings", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(-1);
        }
        //set up FlatLaf
        if (settings.theme == Settings.Theme.Dark) FlatDarculaLaf.setup(); else FlatLightLaf.setup();
        //create the window
        WhispR whispR = new WhispR();
        //set up custom styling for elements here
        whispR.titleLabel.putClientProperty("FlatLaf.styleClass", "h0");
        whispR.authorLabel.putClientProperty("FlatLaf.styleClass", "medium");
    }

    private ImageIcon getScaledImage(ImageIcon srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg.getImage(), 0, 0, w, h, null);
        g2.dispose();

        return new ImageIcon(resizedImg);
    }
}

