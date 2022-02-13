package com.aaroncohen.WhispR;

import javax.swing.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

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
    private JLabel nameLabel;

    private String name;
    private int room;

    public ChatPanel(JPanel parent, String name, int room) {
        //declare instance variables
        this.name = name;
        this.room = room;
        //set up contentPanel
        roomLabel.setText("" + room);
        roomLabel.putClientProperty("FlatLaf.style", "font: bold");
        nameLabel.setText(name);
        nameLabel.putClientProperty("FlatLaf.style", "font: bold");
        exitButton.addActionListener((e -> {
            ChatNetworker.running = false;
            parent.add(new DefaultPanel(parent).getPanel());
            parent.remove(this.contentPanel);
        }));

        //sending messages
        messageField.addActionListener((e -> {
            sendMessage();
        }));
        sendButton.addActionListener((e -> {
            sendMessage();
        }));
    }

    public void sendMessage() {
        if (messageField.getText().equals("")) return;

        try {
            //create the socket and join group
            MulticastSocket socket = new MulticastSocket(room);
            InetAddress group = InetAddress.getByName("230.0.0.0");
            socket.joinGroup(group);

            //create the packet
            String message = "<" + name + ">: " + messageField.getText() + "\n";
            DatagramPacket packet = new DatagramPacket(message.getBytes(), message.getBytes().length, group, room);

            //send packet and leave
            socket.send(packet);
            socket.leaveGroup(group);
            socket.close();

            //now clear the text field
            messageField.setText("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }   //should not be called
    }

    public void addMessage(String msg) {
        chatPane.setText(chatPane.getText() + msg + "\n");
        chatPane.setCaretPosition(chatPane.getDocument().getLength());
    }

    public JPanel getPanel() {
        return contentPanel;
    }
}
