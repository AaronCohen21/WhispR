package com.aaroncohen.ChattR;

import javax.swing.*;
import java.awt.*;
import static com.aaroncohen.ChattR.ChattR.ERROR_RED;
import static com.aaroncohen.ChattR.ChattR.portAvaliable;

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
    private JButton createRoomButton;

    private JPanel parent;

    public DefaultPanel(JPanel parent) {
        this.parent = parent;
        Color defaultTextColor = nameLabel.getForeground();

        //when the join button is clicked
        joinButton.addActionListener((e -> {
            //if statements to change colors of the labels if they're empty
            if (nameField.getText().equals("")) nameLabel.setForeground(ERROR_RED); else nameLabel.setForeground(defaultTextColor);
            if (roomField.getText().equals("")) roomLabel.setForeground(ERROR_RED); else {
                //if the room field isn't empty try to join the room
                boolean joinRoom = false;

                //TODO if the room is valid do nothing and join, otherwise make the label red and don't join
                if (joinRoom && nameField.getText().equals("")) {

                } else roomLabel.setForeground(ERROR_RED);
            }
        }));
        //when the create room button is clicked
        createRoomButton.addActionListener((e -> {
            //if statements to change colors of the labels if they're empty
            if (nameField.getText().equals("")) nameLabel.setForeground(ERROR_RED); else nameLabel.setForeground(defaultTextColor);
            if (roomField.getText().equals("")) roomLabel.setForeground(ERROR_RED); else {
                //if the room field isn't empty try to join the room

                //test if pin is a valid pin, if it is valid the room will be joined
                boolean joinRoom = false;
                try {joinRoom = portAvaliable(Integer.parseInt(roomField.getText()));} catch(Exception ex) {}

                if (joinRoom && !nameField.getText().equals("")) {
                    //change screen
                    ChatPanel chatPanel = new ChatPanel(parent, nameField.getText(), Integer.parseInt(roomField.getText()));
                    parent.add(chatPanel.getPanel());
                    parent.remove(this.contentPanel);

                    //create server
                    Server server = new Server(parent, chatPanel, Integer.parseInt(roomField.getText()));
                    server.start();

                //else statement to set color of room label to red if it isn't valid
                } else {
                    boolean setRedText = true;
                    try {setRedText = !portAvaliable(Integer.parseInt(roomField.getText()));} catch(Exception ex) {}
                    roomLabel.setForeground(setRedText ? ERROR_RED : defaultTextColor);
                    roomField.setText("");
                }
            }
        }));
    }

    public JPanel getPanel() {
        return contentPanel;
    }
}
