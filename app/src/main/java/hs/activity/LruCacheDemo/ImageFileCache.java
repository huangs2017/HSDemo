package hs.activity.LruCacheDemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;

public class ImageFileCache {

    private static final int CACHE_SIZE = 10;
    String catchPath;


    public ImageFileCache() {
        catchPath = Environment.getExternalStorageDirectory().getPath() + "/" + "ImgCache";
        removeCache(catchPath);
    }

    // 从缓存中获取图片
    public Bitmap getImage(final String url) {
        final String filePath = catchPath + "/" + convertUrlToFileName(url);
        File file = new File(filePath);
        if (file.exists()) {
            Bitmap bmp = BitmapFactory.decodeFile(filePath);
            return bmp;
        }
        return null;
    }

    // 将图片存入文件缓存
    public void saveBitmap(Bitmap bm, String url) {
        String filename = convertUrlToFileName(url);
        File dirFile = new File(catchPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File file = new File(catchPath + "/" + filename);
        try {
            file.createNewFile();
            OutputStream outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (IOException e) {

        }
    }

    /**
     * 计算存储目录下的文件大小，
     * 当文件总大小大于规定的CACHE_SIZE, 删除40%最近没有被使用的文件
     */
    private boolean removeCache(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null) {
            return true;
        }
        int dirSize = 0;
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().contains(".cach")) {
                dirSize += files[i].length();
            }
        }
        if (dirSize > CACHE_SIZE * 1024 * 1024) {
            int remove_file_num = (int) ((0.4 * files.length) + 1);
            Arrays.sort(files, new FileSort());
            for (int i = 0; i < remove_file_num; i++) {
                if (files[i].getName().contains(".cach")) {
                    files[i].delete();
                }
            }
        }
        return true;
    }


    // 将url转成文件名
    public static String convertUrlToFileName(String url) {
        String[] strs = url.split("/");
        return strs[strs.length - 1] + ".cach";
    }

    // 根据文件的最后修改时间进行排序
    private class FileSort implements Comparator<File> {
        @Override
        public int compare(File arg0, File arg1) {
            if (arg0.lastModified() > arg1.lastModified()) {
                return 1;
            } else if (arg0.lastModified() == arg1.lastModified()) {
                return 0;
            } else {
                return -1;
            }
        }
    }

}