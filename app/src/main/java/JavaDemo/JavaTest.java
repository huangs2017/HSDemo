package JavaDemo;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class JavaTest {

    boolean running = true;

    public void test() {
        System.out.println("test start...");
        while (running) {
        }
        System. out. println("test end....") ;
    }


    public static void main (String[] args) throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        System.out.println("hostAddress--> " + address.getHostAddress());   // 127.0.0.1
        System.out.println("hostName--> " + address.getHostName());         // localhost

        address = InetAddress.getByName("www.163.com");
        System.out.println("hostAddress--> " + address.getHostAddress());   // 111.202.34.25
        System.out.println("hostName--> " + address.getHostName());         // www.163.com

        address = InetAddress.getByName("61.135.253.15");
        System.out.println("hostAddress--> " + address.getHostAddress());   //  61.135.253.15
        // 如果IP地址不存在或DNS服务器不允许进行IP地址和域名的映射，getHostName方法就返回IP地址
        System.out.println("hostName--> " + address.getHostName());         // 61.135.253.15


        InetSocketAddress socketAddress1 = new InetSocketAddress("127.0.0.1", 8080);
        InetSocketAddress socketAddress2 = new InetSocketAddress("localhost", 9000);

        System.out.println(socketAddress1.getAddress());
        System.out.println(socketAddress1.getHostName());
        System.out.println(socketAddress1.getPort());
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
