package JavaDemo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class IOTest {

    public static void main(String[] args) throws IOException {
        //写出
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        BufferedOutputStream bufOut = new BufferedOutputStream(bytesOut);
        DataOutputStream dataOut = new DataOutputStream(bufOut);  //数据流

        //操作数据类型 + 数据
        dataOut.writeUTF("写入字符串");
        dataOut.writeChar('a');
        dataOut.writeInt(18);
        dataOut.writeBoolean(false);
        dataOut.flush();

        byte[] bytes =bytesOut.toByteArray();
        //读取
        ByteArrayInputStream bytesIn = new ByteArrayInputStream(bytes);
        BufferedInputStream bufIn = new BufferedInputStream(bytesIn);
        DataInputStream dataIn = new DataInputStream(bufIn);

        //顺序与写出一致
        System.out.println( dataIn.readUTF() );
        System.out.println( dataIn.readChar() );
        System.out.println( dataIn.readInt() );
        System.out.println( dataIn.readBoolean() );
    }

}
