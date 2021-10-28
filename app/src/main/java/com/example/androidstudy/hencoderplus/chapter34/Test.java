package com.example.androidstudy.hencoderplus.chapter34;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.hencoderplus.chapter34
 * ClassName: Test
 * CreateDate: 2021/10/28 2:22 下午
 * Author: zjy
 * Description:
 */
public class Test {
    public static void main(String[] args) {
        File file = new File("");
        File copyFile = new File("");
        FileInputStream is = null;
        FileOutputStream os = null;

        try {
            is = new FileInputStream(file);
            os = new FileOutputStream(copyFile);

            byte[] buffer = new byte[1024];
            while (is.read(buffer) != -1) {
                os.write(buffer);
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
