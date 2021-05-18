package com.example.androidstudy.any.fonttest;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.example.androidstudy.R;
import com.example.androidstudy.databinding.ActivityFontTestBinding;
import com.permissionx.guolindev.PermissionX;

import java.io.File;

public class FontTestActivity extends AppCompatActivity {
    private static final String FONT_NAME = "Lobster-1.4.otf";

    private ActivityFontTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFontTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        PermissionX
                .init(this)
                .permissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        loadFont();
                    }
                });

    }

    /**
     * 加载本地字体
     */
    private void loadFont() {
        String fontPath = Environment.getExternalStorageDirectory() + File.separator + FONT_NAME;
        File fontFile = new File(fontPath);
        if (!fontFile.exists()) {
            return;
        }
        Log.i("zjy", "loadFont 文件存在");
        binding.tv2.setText("test 字体");

        // 从sd卡读取字体方法一
//        final AssetManager am = getAssets();
//
//        File outFile = null;
//        try {
//            outFile = File.createTempFile("example", "otf", getCacheDir());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        try (InputStream in = new FileInputStream(fontFile);
//             OutputStream out = new FileOutputStream(outFile)) {
//            byte[] buf = new byte[1024];
//            int n = 0;
//            while ((n = in.read(buf)) != -1) {
//                out.write(buf, 0, n);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        Typeface typeface = Typeface.createFromFile(outFile);
//
//        outFile.delete();

        // 从sd卡读取字体方法二
        Typeface typeface = Typeface.createFromFile(fontPath);
        // 从assets中读取字体
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Lobster-1.4.otf");
        binding.tv2.setTypeface(typeface);

    }
}