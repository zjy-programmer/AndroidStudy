package com.example.androidstudy.any.glide;

import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import androidx.annotation.NonNull;

/**
 * ProjectName: eyepetizer_android
 * Package: com.wandoujia.eyepetizerlive.common.ui
 * ClassName: FitTopTransformation
 * CreateDate: 2021/6/21 6:40 下午
 * Author: zjy
 * Description: 顶部FIT裁切
 */
public class FitTopTransformation extends BitmapTransformation {
    private static final String ID = "com.bumptech.glide.transformations.FitTopTransformation";
    private static final byte[] ID_BYTES = ID.getBytes(StandardCharsets.UTF_8);

    /**
     * @param pool      ""
     * @param source    原图
     * @param outWidth  ImageView的宽度
     * @param outHeight ImageView的高度
     * @return 调整后的图片
     */
    @Override
    public Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap source, int outWidth, int outHeight) {
        return Bitmap.createBitmap(source, 0, 0, outWidth, outHeight);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof FitTopTransformation;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}

