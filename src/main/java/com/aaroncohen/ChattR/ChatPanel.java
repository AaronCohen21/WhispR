package com.aaroncohen.ChattR;

import javax.swing.*;

/**
 * Code written by Aaron Cohen
 * File Created On: 02/10/2022
 */

public class ChatPanel {
    private JPanel contentPanel;
    private JLabel roomLabel;
    private JTextPane chatPane;
    private JTextField messageField;
    private JButton sendButton;
    private JButton exitButton;

    private String name;
    private int room;

    public ChatPanel(JPanel parent, String name, int room) {
        //declare instance variables
        this.name = name;
        this.room = room;
        //set up contentPanel
        roomLabel.setText("Room: " + room + " | Chatting As: " + name);
        exitButton.addActionListener((e -> {
            Server.running = false;
            parent.add(new DefaultPanel(parent).getPanel());
            parent.remove(this.contentPanel);
        }));

        //sending messages
        sendButton.addActionListener((e -> {
            if (messageField.getText().equals("")) return;

            chatPane.setText(chatPane.getText() + name + ": " + messageField.getText() + "\n");
        }));
    }

    public void addMessage(String msg) {
        chatPane.setText(chatPane.getText() + msg + "\n");
    }

    public JPanel getPanel() {
        return contentPanel;
    }
}
