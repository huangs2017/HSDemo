package network.Socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import util.Log;

public class  UDPServer {

    static DatagramSocket server;

    public static void main (String[] args) throws Exception {
        server = new DatagramSocket(8888);
        byte[] bytes = new byte[1024];
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
        server.receive(packet);
        String result = new String(packet.getData(), 0, packet.getLength());
        Log.i("收到消息：" + result);
        server.close();
    }

}
