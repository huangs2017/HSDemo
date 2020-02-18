package network.Socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import util.Log;

public class TCPClient {

	static Socket client;

	public static void main(String[] args) throws IOException, InterruptedException {
		client = new Socket("127.0.0.1", 8888);
		DataOutputStream out = new DataOutputStream(client.getOutputStream());
		DataInputStream in = new DataInputStream(client.getInputStream());

		// 请求信息
		out.writeUTF("黄宋");
		out.flush();

		// 响应信息
		String result = in.readUTF();
		Log.i("服务端返回结果：" + result);

		client.close();

	}



	// 测试（没用）
	private void test1() throws IOException {
		client = new Socket("192.168.3.5", 8080);
		String str = "GET /login/index.jsp HTTP/1.1\n";
		str += "Host: 192.168.3.5:8080\n";
		PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
		out.println(str);
		out.flush();

		InputStream inStream = client.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
		String str1 = null;
		while ((str1 = reader.readLine()) != null) {
			Log.i(str1);
		}
	}

	// 测试（没用）
	private void test2() throws IOException {
		client = new Socket("192.168.3.6", 8080);
		String str = "POST /login/check HTTP/1.1\n";
		str +="Content-Length: 22\n";
		str +="Content-Type: application/x-www-form-urlencoded\n";
		str += "Host: 192.168.3.6:8080\n\n"; // 俩\n
		str +="name=linlin&pwd=tanqiu";
		PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
		out.println(str);
		out.flush();

		InputStream inStream = client.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
		String str1;
		while ((str1 = reader.readLine()) != null) {
			Log.i(str1);
		}
	}


}
