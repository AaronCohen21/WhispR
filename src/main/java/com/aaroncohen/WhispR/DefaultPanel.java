package com.aaroncohen.WhispR;

import javax.swing.*;
import java.awt.*;
import static com.aaroncohen.WhispR.WhispR.ERROR_RED;

/**
 * Code written by Aaron Cohen
 * File Created On: 02/10/2022
 */

public class DefaultPanel {

    private JPanel contentPanel;
    private JLabel roomLabel;
    private JTextField roomField;
    private JLabel nameLabel;
    private JTextField nameField;
    private JButton joinButton;

    private JPanel parent;

    public DefaultPanel(JPanel parent) {
        this.parent = parent;
        Color defaultTextColor = nameLabel.getForeground();

        //when the join room button is clicked
        joinButton.addActionListener((e -> {
            //if the name field is empty make the label red
            if (nameField.getText().equals("")) nameLabel.setForeground(ERROR_RED); else nameLabel.setForeground(defaultTextColor);

            //test if the room is valid
            boolean validRoom = true;
            try {
                if (Integer.parseInt(roomField.getText()) > 4096 && Integer.parseInt(roomField.getText()) < 35565) {
                    //do nothing, the room is valid
                } else validRoom = false;
            } catch (Exception ex) {validRoom = false;}

            //check if the room is valid
            if (!validRoom) {
                roomLabel.setForeground(ERROR_RED);
                roomField.setText("");
            } else {
                roomLabel.setForeground(defaultTextColor);
                //if the name field isn't empty try to join the room
                if (!nameField.getText().equals("")) {
                    //change screen
                    ChatPanel chatPanel = new ChatPanel(parent, nameField.getText(), Integer.parseInt(roomField.getText()));
                    parent.add(chatPanel.getPanel());
                    parent.remove(this.contentPanel);

                    //create chatNetworker
                    ChatNetworker chatNetworker = new ChatNetworker(parent, chatPanel, Integer.parseInt(roomField.getText()));
                    chatNetworker.start();
                }
            }
        }));
    }

    public JPanel getPanel() {
        return contentPanel;
    }
}
