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
//        String fontPath = Environment.getExternalStorageDirectory() + File.separator + FONT_NAME;
//        File fontFile = new File(fontPath);
//        if (!fontFile.exists()) {
//            return;
//        }
//        Log.i("zjy", "loadFont 文件存在");
//        binding.tv1.setText("测试 GlowSansSC-Normal-Light.otf 字体 GgJjAaYy");
        binding.tv1.setText("来到布达佩斯的城堡山，很容易就能看到这座拥有石造尖塔、镶嵌着彩色马赛克屋顶的马加什教堂。这座建筑的外观色泽亮丽，属新哥特式教堂，蕴含了匈牙利民俗、新艺术风格和土耳其风格等多种元素。\n" +
                "而教堂内部的彩绘玻璃窗和顶棚画，更是不可错过的亮点。马加什教堂从13世纪至今历经多次修整，见证了改朝换代的时代变迁。");
        binding.tv2.setText("测试 GlowSansSC-Normal-Medium.otf 字体 GgJjAaYy");
        binding.tv3.setText("测试 GlowSansSC-Normal-Regular.otf 字体 GgJjAaYy");

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
//        Typeface typeface = Typeface.createFromFile(fontPath);
        // 从assets中读取字体
        Typeface typeface1 = Typeface.createFromAsset(getAssets(), "fonts/GlowSansSC-Normal-Light.otf");
        Typeface typeface2 = Typeface.createFromAsset(getAssets(), "fonts/GlowSansSC-Normal-Medium.otf");
        Typeface typeface3 = Typeface.createFromAsset(getAssets(), "fonts/GlowSansSC-Normal-Regular.otf");
        binding.tv1.setTypeface(typeface1);
        binding.tv2.setTypeface(typeface2);
        binding.tv3.setTypeface(typeface3);

    }
}