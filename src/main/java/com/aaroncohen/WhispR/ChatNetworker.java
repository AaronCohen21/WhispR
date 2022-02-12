package com.aaroncohen.WhispR;

import javax.swing.*;
import java.net.*;

import static com.aaroncohen.WhispR.WhispR.settings;

/**
 * Code written by Aaron Cohen
 * File Created On: 02/10/2022
 */

public class ChatNetworker extends Thread {

    private int port;
    private JPanel parent;
    private ChatPanel chatPanel;

    public static boolean running = false;

    public ChatNetworker(JPanel parent, ChatPanel chatPanel, int port) {
        this.parent = parent;
        this.chatPanel = chatPanel;
        this.port = port;
    }

    @Override
    public void run() {
        running = true;
        try {
            MulticastSocket socket = new MulticastSocket(port);
            socket.setSoTimeout(5000);
            byte[] buf = new byte[256];
            InetAddress group = InetAddress.getByName("230.0.0.0");
            socket.joinGroup(group);
            //now that the client is successfully connected, save the room code
            settings.lastRoomCode = "" + port;
            while (running) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                try {
                    //when receiving a packet, display the message on the screen
                    socket.receive(packet);
                    //update the host's chat board
                    String received = new String(packet.getData(), 0, packet.getLength());
                    chatPanel.addMessage(received);
                } catch (SocketTimeoutException e) {}
            }
            socket.leaveGroup(group);
            socket.close();
        } catch (Exception e) {
            parent.add(new DefaultPanel(parent).getPanel());
            parent.remove(chatPanel.getPanel());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getClass().toString() + " " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
