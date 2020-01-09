package hs.activity.BreakpointDownload;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.lang.ref.PhantomReference;
import java.net.HttpURLConnection;
import java.net.URL;
import hs.activity.R;

// 多线程断点续传
public class DownloadActivity extends AppCompatActivity {

    static String path = "http://192.168.0.90:8080/DestineFoodServer/test.avi";
    static int threadNum = 3;   // 线程数量
    static int count;           // 线程完成数量
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ctx = this;
        verifyStoragePermissions();
    }


    public void bind(View view) {

        try {
            // FileOutputStream openFileOutput(String name, @FileMode int mode);
            // 默认是Context.MODE_PRIVATE模式，即别的应用程序不能访问它。
            UserInfo person = new UserInfo("hunter", 20);
            File fileDir = getExternalFilesDir("");
            File file = new File(getExternalFilesDir(""),"file123");
            System.out.println("hunter----------11" + fileDir.getAbsolutePath());


//       1.对象的序列化
            FileOutputStream fileOutput;
//            fileOutput = openFileOutput("aa", Context.MODE_PRIVATE);
            fileOutput = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutput);
            oos.writeObject(person);
            oos.close();


            //2.对象的反序列化
            FileInputStream fileInput;
//            fileInput = openFileInput("aa");
            fileInput = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fileInput);
            UserInfo stu = (UserInfo) ois.readObject();//强制类型转化

            System.out.println("hunter----------");
            System.out.println(stu);
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    downloadPrepare();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
    }

    // 获取服务器文件大小，计算每个线程下载开始位置和结束位置
    public void downloadPrepare() throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        int code = conn.getResponseCode();
        if (code == 200) {
            int length = conn.getContentLength();
            System.out.println("length:" + length);
            // 创建一个和服务器大小一样的文件，目的是提前把空间申请出来
            RandomAccessFile file = new RandomAccessFile(getFileName(path), "rw");
            file.setLength(length);

            // 算出每个线程下载的大小
            int blockSize = length / threadNum;
            for (int i = 0; i < threadNum; i++) {
                int startPosition = i * blockSize; // 每个线程下载开始的位置
                int endPosition;
                if (i == threadNum - 1) {
                    endPosition = length - 1; // 特殊情况，最后一个线程
                } else {
                    endPosition = (i + 1) * blockSize - 1;
                }
                System.out.println("线程id：" + i + "理论下载位置：" + startPosition + "-->" + endPosition);
                // 开启线程去下载文件
                new DownloadThread(ctx, i, startPosition, endPosition).start();
            }
        }
    }


    public static String getFileName(String path) {
        int start = path.lastIndexOf("/") + 1;
        String substring = path.substring(start);
        String fileName = Environment.getExternalStorageDirectory().getPath() + "/" + substring;
        return fileName;
    }



    // 动态获取内存存储权限，手机Android6.0后，谷歌增加了许多权限验证
    public void verifyStoragePermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            int i = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (i == PackageManager.PERMISSION_GRANTED) {
                return;
            }

            int REQUEST_CODE_PERMISSION_STORAGE = 100;
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            for (String str : permissions) {
                if (checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(permissions, REQUEST_CODE_PERMISSION_STORAGE);
                    return;
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            // 执行操作
        }
    }

}




/*
    多线程加速下载
        原理
        1. 不是说线程开的越多下载越快（迅雷app，建议开3-4个线程）
        2. 还受服务器带宽的影响
        3. 更多的cup资源给了你

        原理
        1. 获取文件大小
        2. 在客户端创建一个大小和服务器一样的文件，提前申请好空间
            2.1 每个线程下载的开始位置和结束位置
        3. 开多个线程去下载文件
        4. 知道每个线程什么时候下载完毕了
*/