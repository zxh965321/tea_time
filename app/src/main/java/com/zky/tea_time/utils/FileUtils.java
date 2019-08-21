package com.zky.tea_time.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    /**
     * 获得图片地址
     * @param name
     * @return
     */
    public static String getImageString(String name){
        String filepath = getSDCardPath() + File.separator + name;
        byte[] bytes = file2Byte(filepath);
        String image = Base64.encodeToString(bytes, Base64.DEFAULT);
        return image;
    }

    public static void write(byte[] content,  String name) {
        writeBytes(getSDCardPath() + File.separator + name, content);
    }

    public static String getSDCardPath(){
        File parentPath = Environment.getExternalStorageDirectory();
        return parentPath.getAbsolutePath();
    }

    private static boolean writeBytes(@NotNull String filePath, @Nullable byte[] bytes) {

        if (bytes != null && bytes.length != 0 && !TextUtils.isEmpty(filePath)) {
            FileOutputStream fos = null;

            try {
                File file = new File(filePath);
                if (!file.exists()) {
                    String fileDir = file.getParent();
                    createDirs(fileDir);
                }

                fos = new FileOutputStream(file);
                fos.write(bytes);
                return true;
            } catch (FileNotFoundException var9) {
                var9.printStackTrace();
            } catch (IOException var10) {
                var10.printStackTrace();
            } finally {
                close((Closeable)fos);
            }
            return false;
        } else {
            return false;
        }

    }

    private static boolean createDirs(@NotNull String dirPath) {
        File file = new File(dirPath);
        return !file.exists() && !file.isDirectory() ? file.mkdirs() : false;
    }

    private static void close(@Nullable Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    //filepath转byte
    public static byte[] file2Byte(String filePath)
    {
        byte[] buffer = null;
        try
        {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return buffer;
    }

}
