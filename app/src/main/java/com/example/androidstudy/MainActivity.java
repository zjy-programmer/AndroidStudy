package com.example.androidstudy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Process;
import android.view.View;

import com.example.androidstudy.any.addressphoto.activity.AddressPhotoActivity;
import com.example.androidstudy.any.contentprovider.ContentProviderTestActivity;
import com.example.androidstudy.any.coordinatorlayout.CoordinatorLayoutTestActivity;
import com.example.androidstudy.any.customview.CustomViewActivity;
import com.example.androidstudy.any.db.SqlLiteDBActivity;
import com.example.androidstudy.any.fonttest.FontTestActivity;
import com.example.androidstudy.any.loadclass.LoadClassTestActivity;
import com.example.androidstudy.any.nestedscroll.NestedScrollActivity;
import com.example.androidstudy.any.recyclerview.RecyclerviewActivity;
import com.example.androidstudy.any.reparent.ReparentActivity;
import com.example.androidstudy.any.rxffmpegtest.RxFFmpegActivity;
import com.example.androidstudy.any.rxjava.RxjavaTestActivity;
import com.example.androidstudy.databinding.ActivityMainBinding;
import com.example.androidstudy.hencoder.HenCoderActivity;
import com.example.androidstudy.hencoderplus.HenCoderPlusActivity;
import com.example.baselibrary.activity.BaseActivity;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Debug.startMethodTracing();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // 会把trace文件放在/mnt/sdcard/Android/包名/files/dmtrace.trace
        Debug.stopMethodTracing();
//        testAsDebugMode();

    }

    /**
     * 这个方法可以判断是否是在AndroidStudio的debug调试模式
     */
    private void testAsDebugMode() {
        if (Debug.isDebuggerConnected()) {
            finish();
            Process.killProcess(Process.myPid());
        }
    }

    public void toAddressPhoto(View view) {
        toActivity(AddressPhotoActivity.class);
    }


    public void toFontTest(View view) {
        toActivity(FontTestActivity.class);
    }

    public void toCoordinatorLayout(View view) {
        toActivity(CoordinatorLayoutTestActivity.class);
    }

    public void toNestedScroll(View view) {
        toActivity(NestedScrollActivity.class);
    }

    public void toCustomViewTest(View view) {
        toActivity(CustomViewActivity.class);
    }

    public void toHenCoderTest(View view) {
        toActivity(HenCoderActivity.class);
    }

    public void toDBTest(View view) {
        toActivity(SqlLiteDBActivity.class);
    }

    public void toContentProvider(View view) {
        toActivity(ContentProviderTestActivity.class);
    }

    public void toRecyclerView(View view) {
        toActivity(RecyclerviewActivity.class);
    }

    public void toHenCoderPlusTest(View view) {
        toActivity(HenCoderPlusActivity.class);
    }

    public void toFFmpeg(View view) {
        toActivity(RxFFmpegActivity.class);
    }

    public void toRxjavaTest(View view) {
        toActivity(RxjavaTestActivity.class);
    }

    // allowTaskReparenting 在android9和android10被移除了 android11又加回来了
    public void toReparentTest(View view) {
        Intent intent = new Intent();
        intent.setAction("zjy.reparent.b");
        intent.addCategory("zjy.reparent.category");
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void toLoadClassTest(View view) {
        toActivity(LoadClassTestActivity.class);
    }

    // 这是用来测试git commit --amend用的数据

    // 测试git rebase -i的edit 修改一下？

    // 再测试git rebase -i的reword 上边的edit模式是可以同时修改文件和commit的message的 reword应该只能修改commit的message 貌似一样啊 也能改文件啊
}