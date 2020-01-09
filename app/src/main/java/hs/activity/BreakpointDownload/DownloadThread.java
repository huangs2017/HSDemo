package hs.activity.BreakpointDownload;

import android.content.Context;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import util.SPUtil;

public class DownloadThread extends Thread {

    Context ctx;
    int threadId;
    int startPosition; // 每个线程下载开始的位置
    int endPosition;

    public DownloadThread(Context ctx, int threadId, int startPosition, int endPosition) {
        this.ctx = ctx;
        this.threadId = threadId;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    @Override
    public void run() {
        try {
            download();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void download() throws Exception {
        int lastPosition = SPUtil.getInt(ctx, "downloadPosition" + threadId, 0); //已经下载的长度
        if (lastPosition > 0) {
            startPosition = lastPosition + 1;
            System.out.println("线程id：" + threadId + "实际下载位置：" + startPosition + "-->" + endPosition);
        }

        URL url = new URL(DownloadActivity.path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Range", "bytes=" + startPosition + "-" + endPosition);

        int code = conn.getResponseCode();
        if (code == 206) { //200请求全部资源成功，206请求部分资源成功
            RandomAccessFile file = new RandomAccessFile(DownloadActivity.getFileName(DownloadActivity.path), "rw");
            file.seek(startPosition);
            InputStream in = conn.getInputStream();
            int len = -1;
            byte[] bytes = new byte[1024]; // 下载进度慢一些，便于测试
//            byte[] bytes = new byte[1024 * 1024];// 1Mb
            int total = 0; // 当前线程下载的大小
            while ((len = in.read(bytes)) != -1) {
                file.write(bytes, 0, len);
                total = total + len;
                int downloadPosition = startPosition + total;
                // 保存当前线程下载位置，下次断点续传
                SPUtil.putInt(ctx, "downloadPosition" + threadId, downloadPosition);
            }
            file.close();
            System.out.println("线程id：" + threadId + "--下载完毕了");

            synchronized (DownloadThread.class) {
                DownloadActivity.count++;
                if (DownloadActivity.count == DownloadActivity.threadNum) {
                    for (int i = 0; i < DownloadActivity.threadNum; i++) {
                        SPUtil.remove(ctx, "downloadPosition" + i);
                    }
                }
            }
        }
    }

}

/*
    0理论下载位置：0-->3412 0649
    1理论下载位置：34120650-->6824 1299
    2理论下载位置：68241300-->1023 6194 9

    <int name="downloadPosition0" value="3698 688" />
    <int name="downloadPosition1" value="3782 0362" />
    <int name="downloadPosition2" value="7193 9988" />
    0实际下载位置：3698 689
    1实际下载位置：3781 9339 (少1023)
    2实际下载位置：7193 9989

    <int name="downloadPosition0" value="7742 465" /> 
    <int name="downloadPosition1" value="4186 3115" />
    <int name="downloadPosition2" value="7598 4789" />
    0实际下载位置：7742 466
    1实际下载位置：4186 3116
    2实际下载位置：7598 3766  (少1023)

    <int name="downloadPosition0" value="2793 3698" />
     <int name="downloadPosition1" value="6205 4348" />
    <int name="downloadPosition2" value="9617 3974" />
    0实际下载位置：2793 3699
    1实际下载位置：6205 3325 (少1023)
    2实际下载位置：9617 3975

    <int name="downloadPosition0" value="32663555" />
     <int name="downloadPosition1" value="66782157" />
    <int name="downloadPosition2" value="1009 0383 1" />
    0实际下载位置：3266 3556
    1实际下载位置：6678 2158
    2实际下载位置：1009 0383 2
*/