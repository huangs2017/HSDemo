import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import Util.Log;

public class JavaTest {

    boolean running = true;

    public void test() {
        Log.i("test start...");
        while (running) {
        }
        Log.i("test end....") ;
    }


    public static void main (String[] args) throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        Log.i("hostAddress--> " + address.getHostAddress());   // 127.0.0.1
        Log.i("hostName--> " + address.getHostName());         // localhost

        address = InetAddress.getByName("www.163.com");
        Log.i("hostAddress--> " + address.getHostAddress());   // 111.202.34.25
        Log.i("hostName--> " + address.getHostName());         // www.163.com

        address = InetAddress.getByName("61.135.253.15");
        Log.i("hostAddress--> " + address.getHostAddress());   //  61.135.253.15
        // 如果IP地址不存在或DNS服务器不允许进行IP地址和域名的映射，getHostName方法就返回IP地址
        Log.i("hostName--> " + address.getHostName());         // 61.135.253.15


        InetSocketAddress socketAddress1 = new InetSocketAddress("127.0.0.1", 8080);
        InetSocketAddress socketAddress2 = new InetSocketAddress("localhost", 9000);

        Log.i(socketAddress1.getAddress());
        Log.i(socketAddress1.getHostName());
        Log.i(socketAddress1.getPort());
//        BufferedInputStream


        JavaTest demo = new JavaTest() ;
//        new Thread(demo :: test, "t1").start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                demo.test();
//            }
//        }, "t1").start();

        new Thread(() -> demo.test(), "t1").start();
        new Thread(demo::test, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        demo. running = false;
    }

}
