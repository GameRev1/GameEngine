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
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 1635598
 */
public class Client extends Thread {
    private SocketAddress address;
    private InetAddress ip4;
    private static Client instance;
    private int socketAdress = 62502;
    private DatagramSocket socket;
    private final byte[] buf = new byte[256];
    
    public static Client getInstance() {
        if(instance == null) {
            instance = new Client();
        }
        return instance;
    }
    
    private Client() {
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
    
    public void sendData(String message) {
        try {
            
            byte[] binaryMessage = message.getBytes();
            DatagramPacket packet = new DatagramPacket(binaryMessage, binaryMessage.length, ip4, socketAdress);
            socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    public ArrayListNetwork receiveMessage() {
        try {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            InetAddress ip = socket.getInetAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, ip, port);
            String getData = new String(packet.getData(), 0, packet.getLength());
            return new ArrayListNetwork(getData);
        } catch (IOException ex) {
            Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public void run() {
        while(Game.playing && Game.netMode != 0) {
            System.out.println("Client Sending data");
        }
    }
}
