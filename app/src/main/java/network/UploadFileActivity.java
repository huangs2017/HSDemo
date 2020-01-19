package network;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import hs.activity.R;

public class UploadFileActivity extends AppCompatActivity {
    // 要上传的文件路径
    private String uploadFile = Environment.getExternalStorageDirectory().getPath() + "/Desert.jpg";
    // 服务器上接收文件的处理页面，这里根据需要换成自己的
    private String actionUrl = "http://222.195.151.19/receive_file.php";

    private TextView mText1;
    private TextView mText2;
    private Button mButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);
        mText1 = findViewById(R.id.myText1);
        mText2 = findViewById(R.id.myText2);
        mButton = findViewById(R.id.myButton);

        mText1.setText("文件路径：\n" + uploadFile);
        mText2.setText("上传网址：\n" + actionUrl);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile(actionUrl);
            }
        });
    }

    /* 上传文件至Server，uploadUrl：接收文件的处理页面 */
    private void uploadFile(String uploadUrl) {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "******";
        try {
            URL url = new URL(uploadUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            // 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
            // 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。
            // httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
            // 允许输入输出流
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            // 使用POST方法
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\"; filename=\""
                    + uploadFile.substring(uploadFile.lastIndexOf("/") + 1)
                    + "\""
                    + end);
            dos.writeBytes(end);

            FileInputStream fis = new FileInputStream(uploadFile);
            byte[] buffer = new byte[1024 * 8]; // 8k
            int len = 0;
            while ((len = fis.read(buffer)) != -1) {
                dos.write(buffer, 0, len);
            }
            fis.close();

            dos.writeBytes(end);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            dos.flush();

            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String result = br.readLine();
            dos.close();
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}