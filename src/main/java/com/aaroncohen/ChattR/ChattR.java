package com.aaroncohen.ChattR;

import com.formdev.flatlaf.*;
import javax.swing.*;
import java.awt.*;

public class ChattR extends JFrame {

    private JPanel defaultPanel;
    private JLabel titleLabel;
    private JLabel authorLabel;
    private JPanel contentPanel;
    private JTextField roomField;
    private JTextField nameField;
    private JButton joinButton;
    private JLabel nameLabel;
    private JLabel roomLabel;
    private JButton createRoomButton;

    private static final Color ERROR_RED = new Color(255, 71, 71);

    public ChattR() {
        setContentPane(defaultPanel);
        setTitle("ChattR");
        setSize(new Dimension(574, 435));
        setMinimumSize(getSize());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        //set up components here
        Color defaultTextColor = titleLabel.getForeground();
        //when the join button is clicked
        joinButton.addActionListener((e -> {
            //if fields are empty
            if (nameField.getText().equals("")) nameLabel.setForeground(ERROR_RED); else nameLabel.setForeground(defaultTextColor);
            if (roomField.getText().equals("")) roomLabel.setForeground(ERROR_RED); else {
                roomLabel.setForeground(defaultTextColor);
                //if the room is valid do nothing and join, otherwise make the label red and don't join
                if (false) {

                } else roomLabel.setForeground(ERROR_RED);
            }
        }));
        //when the create room button is clicked
        createRoomButton.addActionListener((e -> {

        }));
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
}
