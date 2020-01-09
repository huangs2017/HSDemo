package 网络.Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	static ServerSocket server;
	static Socket client;

	public static void main(String[] args) throws IOException, InterruptedException {
		server = new ServerSocket(8888);
		client = server.accept();
		System.out.println("一个客户端建立了连接");
		DataInputStream in = new DataInputStream(client.getInputStream());
		DataOutputStream out = new DataOutputStream(client.getOutputStream());

		// 请求信息
		String requestMsg = in.readUTF();
		System.out.println("用户登录：" + requestMsg);

		// 响应信息
		out.writeUTF("登录成功：" + requestMsg);
		out.flush();

		in.close();
		client.close();
		server.close();
	}

}
