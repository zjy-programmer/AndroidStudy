package com.example.androidstudy.any.imagepicker

import android.os.Bundle
import com.example.androidstudy.databinding.ActivityImagePickerTestBinding
import com.example.baselibrary.activity.BaseActivity
import com.ypx.imagepicker.ImagePicker
import com.ypx.imagepicker.bean.ImageItem
import com.ypx.imagepicker.bean.MimeType
import com.ypx.imagepicker.bean.PickerError
import com.ypx.imagepicker.bean.SelectMode
import com.ypx.imagepicker.data.OnImagePickCompleteListener2
import java.util.ArrayList

class ImagePickerTestActivity : BaseActivity() {
    lateinit var binding: ActivityImagePickerTestBinding
    lateinit var krImgPickerPresenter: KrImgPickerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagePickerTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        krImgPickerPresenter =
            KrImgPickerPresenter()

        binding.btn.setOnClickListener {
            ImagePicker.withMulti(krImgPickerPresenter)
                .setMaxCount(9)
                .setColumnCount(4)
                .setOriginal(false)
                .mimeTypes(MimeType.ofImage())
                .setSelectMode(SelectMode.MODE_MULTI)
                .showCamera(true)
                .setPreview(true)
                .pick(this, object : OnImagePickCompleteListener2 {
                    override fun onImagePickComplete(items: ArrayList<ImageItem>?) {

                    }

                    override fun onPickFailed(error: PickerError?) {

                    }
                })
        }
    }
}