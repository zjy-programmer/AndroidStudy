package com.example.androidstudy.any.coroutines.flowpractice.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.androidstudy.any.coroutines.flowpractice.download.DownloadManager
import com.example.androidstudy.any.coroutines.flowpractice.download.DownloadStatus
import com.example.androidstudy.databinding.FragmentDownloadBinding
import kotlinx.coroutines.flow.collect
import java.io.File


class DownloadFragment : Fragment() {

    val URL = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fdpic.tiankong.com%2Fk2%2Flu%2FQJ8656178033.jpg&refer=http%3A%2F%2Fdpic.tiankong.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1659780582&t=9b22243b4ada40804b58ccda5cac6a2c"

    private val binding: FragmentDownloadBinding by lazy {
        FragmentDownloadBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenCreated {
            context?.apply {
                val file = File(getExternalFilesDir(null)?.path, "pic.JPG")
                DownloadManager.download(URL, file).collect { status ->
                    when (status) {
                        is DownloadStatus.Progress -> {
                            binding.apply {
                                progressBar.progress = status.value
                                tvProgress.text = "${status.value}"
                            }
                        }
                        is DownloadStatus.Error -> {
                            Toast.makeText(context, "下载错误", Toast.LENGTH_SHORT).show()
                        }
                        is DownloadStatus.Done -> {
                            binding.apply {
                                progressBar.progress = 100
                                tvProgress.text = "100"
                            }
                            Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}