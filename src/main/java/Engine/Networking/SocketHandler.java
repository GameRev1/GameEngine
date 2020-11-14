/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Networking;

import Engine.Game;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 1635598
 */
public class SocketHandler extends Thread{
    private SocketAddress address;
    private InetAddress ip4;
    private static SocketHandler instance;
    private int socketAdress = 62502;
    private DatagramSocket socket;
    private final byte[] buf = new byte[Short.MAX_VALUE];
    
    public static SocketHandler getInstance() {
        if(instance == null) {
            instance = new SocketHandler();
        }
        return instance;
    }
    
    private SocketHandler() {
        declareSocket();
    }
    
    private void declareSocket() {
        try {
            ip4 = (Inet4Address) InetAddress.getLocalHost();
            if(Game.netMode == 1) {
                ip4 = InetAddress.getByName("localhost");
                socket = new DatagramSocket();
            } else {
                ip4 = InetAddress.getByName("localhost");
                socket = new DatagramSocket(socketAdress);
                System.out.println("socket address : " + socket);
            }
            System.out.println("socket address : " + socket);
        } catch (IOException ex) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendData(ArrayListNetwork message) {
        try {
            String data = message.convert();
            byte[] binaryMessage = data.getBytes();
            DatagramPacket packet = new DatagramPacket(binaryMessage, binaryMessage.length, ip4, socketAdress);
            System.out.println(packet);
            System.out.println(socket);
            socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    public void receiveMessage() {
        try {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            InetAddress ip = socket.getInetAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, ip, port);
            
        } catch (IOException ex) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        while(Game.playing && Game.netMode != 0) {
            if(Game.netMode == 2) {
                System.out.println("Server receiving data...");
                receiveMessage();
            } else if(Game.netMode == 1) {
                System.out.println("Client Sending data");
            }
        }
    }
    
    public void processData(byte[] data) {
        ArrayListNetwork convertedData = new ArrayListNetwork(new String(data, data.length, 2));
        int messageType = convertedData.readInt();
        switch(messageType) {
            case 0 : 
                
        }
    }
}
