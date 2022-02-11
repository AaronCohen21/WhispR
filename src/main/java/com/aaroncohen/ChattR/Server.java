package com.aaroncohen.ChattR;

import javax.swing.*;
import java.net.*;

/**
 * Code written by Aaron Cohen
 * File Created On: 02/10/2022
 */

public class Server extends Thread {

    private int port;
    private JPanel parent;
    private ChatPanel chatPanel;

    public static boolean running = false;

    public Server(JPanel parent, ChatPanel chatPanel, int port) {
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
            while (running) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                try {
                    //when receiving a packet, the server will simply re-broadcast the packet which will then show up on all clients
                    socket.receive(packet);
                    //update the host's chat board
                    String received = new String(packet.getData(), 0, packet.getLength());
                    chatPanel.addMessage(received);
                    //broadcast
                    socket.send(packet);
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
