package 网络;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LongConnectionTest {

    public static void main(String[] args) throws IOException {

        URL url = new URL("http://192.168.0.90:8080/DestineFoodServer/longConnection");
        HttpURLConnection client = (HttpURLConnection) url.openConnection();
        client.setRequestMethod("POST");
        client.setDoOutput(true);
        client.setDoInput(true);

        int i = 1;
        // 告诉HttpURLConnection数据的大小
//        client.setFixedLengthStreamingMode(8*6);
        // 分块传输, 每当缓存中的数据量累计到设定的值后，java就会把缓存的中的数据输出
        client.setChunkedStreamingMode(8); //默认值1024
        OutputStream out = client.getOutputStream();
        while(true) {
            String str = "name=hs" + i;
            byte[] bs = str.getBytes();
            out.write(bs);
            System.out.println(bs.length + "-->" + i++);
            try {
                Thread.sleep(1000 * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            InputStream in = client.getInputStream();
            int len = -1;
            byte[] b = new byte[1024];
            while ((len = in.read(b)) != -1) {
                String s = new String(b, 0, len, "UTF-8");
                System.out.println(s);
            }
        }

    }

}
