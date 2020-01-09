package 网络.Socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {

    static DatagramSocket client;

    public static void main (String[] args) throws Exception {
        String msg = "测试消息";
        byte[] bytes = msg.getBytes();
        client = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, InetAddress.getByName("255.255.255.255"), 8888);
        client.send(packet);
    }

}