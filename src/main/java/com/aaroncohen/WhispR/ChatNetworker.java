package com.aaroncohen.WhispR;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.net.*;

import static com.aaroncohen.WhispR.WhispR.settings;

/**
 * Code written by Aaron Cohen
 * File Created On: 02/10/2022
 */

public class ChatNetworker extends Thread implements FileLoader {

    private int port;
    private JPanel parent;
    private ChatPanel chatPanel;
    private String name;

    public static boolean running = false;
    private AudioThread audioThread;

    public static int unreadMessages;

    public ChatNetworker(JPanel parent, ChatPanel chatPanel, int port, String name) {
        this.parent = parent;
        this.chatPanel = chatPanel;
        this.port = port;
        this.name = name;
        unreadMessages = -1;
    }

    @Override
    public void run() {
        running = true;
        try {
            MulticastSocket socket = new MulticastSocket(port);
            socket.setSoTimeout(1000);
            byte[] buf = new byte[256];
            SocketAddress group = new InetSocketAddress("230.0.0.0", port);
            NetworkInterface networkInterface = NetworkInterface.getByName("230.0.0.0");
            socket.joinGroup(group, networkInterface);
            //now that the client is successfully connected, save the room code
            settings.lastRoomCode = "" + port;
            //send a join message
            sendMessage(name + " has joined the room\n");
            while (running) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                try {
                    //when receiving a packet, display the message on the screen
                    socket.receive(packet);
                    //update the host's chat board
                    String received = new String(packet.getData(), 0, packet.getLength());
                    chatPanel.addMessage(received);
                    if (settings.notificationSounds) playSound();
                } catch (SocketTimeoutException e) {}
            }
            //send a leave message
            sendMessage(name + " has left the room\n");
            socket.leaveGroup(group, networkInterface);
            socket.close();
        } catch (Exception e) {
            parent.add(new DefaultPanel(parent).getPanel());
            parent.remove(chatPanel.getPanel());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getClass().toString() + " " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sendMessage(String msg) {
        try {
            //create the socket and join group
            MulticastSocket socket = new MulticastSocket(port);
            InetAddress group = InetAddress.getByName("230.0.0.0");
            socket.joinGroup(group);

            //create the packet
            DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length, group, port);

            //send packet and leave
            socket.send(packet);
            socket.leaveGroup(group);
            socket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }   //should not be called
    }

    public void playSound() {
        try {audioThread.clip.stop();} catch (Exception ignored) {}
        audioThread = new AudioThread();
        audioThread.start();
    }

    private class AudioThread extends Thread {

        public Clip clip;

        public void run() {
            try {
                clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(
                        getFileFromResourceAsStream("pop.wav")));
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
