package com.example.androidstudy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Process;
import android.view.View;

import com.example.androidstudy.any.Test1;
import com.example.androidstudy.any.addressphoto.activity.AddressPhotoActivity;
import com.example.androidstudy.any.contentprovider.ContentProviderTestActivity;
import com.example.androidstudy.any.coordinatorlayout.CoordinatorLayoutTestActivity;
import com.example.androidstudy.any.coroutines.CoroutinesTestActivity;
import com.example.androidstudy.any.customview.CustomViewActivity;
import com.example.androidstudy.any.db.SqlLiteDBActivity;
import com.example.androidstudy.any.fonttest.FontTestActivity;
import com.example.androidstudy.any.imagepicker.ImagePickerTestActivity;
import com.example.androidstudy.any.loadclass.LoadClassTestActivity;
import com.example.androidstudy.any.nestedscroll.NestedScrollActivity;
import com.example.androidstudy.any.recyclerview.RecyclerviewActivity;
import com.example.androidstudy.any.reparent.ReparentActivity;
import com.example.androidstudy.any.rxffmpegtest.RxFFmpegActivity;
import com.example.androidstudy.any.rxjava.RxjavaTestActivity;
import com.example.androidstudy.any.smartrefresh.SmartRefreshLayoutTestActivity;
import com.example.androidstudy.any.tablayout.TabLayoutActivity;
import com.example.androidstudy.databinding.ActivityMainBinding;
import com.example.androidstudy.hencoder.HenCoderActivity;
import com.example.androidstudy.hencoderplus.HenCoderPlusActivity;
import com.example.baselibrary.activity.BaseActivity;

import leakcanary.AppWatcher;
import leakcanary.LeakCanary;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private String s = "111";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Debug.startMethodTracing();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // ??????trace????????????/mnt/sdcard/Android/??????/files/dmtrace.trace
//        Debug.stopMethodTracing();
//        testAsDebugMode();

        // LeakCanary????????????object????????????????????????watch(???????????? ??????expectWeaklyReachable??????????????????)5????????????????????? ?????????????????????????????????
//        AppWatcher.INSTANCE.getObjectWatcher().expectWeaklyReachable(s, "??????????????????s????????????");
    }

    /**
     * ????????????????????????????????????AndroidStudio???debug????????????
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

    // allowTaskReparenting ???android9???android10???????????? android11???????????????
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

    public void toCoroutinesTest(View view) {
        toActivity(CoroutinesTestActivity.class);
    }

    public void toTabLayoutTest(View view) {
        toActivity(TabLayoutActivity.class);
    }

    public void toSmartRefreshLayoutTest(View view) {
        toActivity(SmartRefreshLayoutTestActivity.class);

    }

    public void toImagePickerTest(View view) {
//        toActivity(ImagePickerTestActivity.class);
        Test1 test1 = new Test1();
        test1.a();
        test1.b();
    }

    // ??????????????????git commit --amend????????????

    // ??????git rebase -i???edit ???????????????

    // ?????????git rebase -i???reword ?????????edit????????????????????????????????????commit???message??? reword??????????????????commit???message ??????????????? ??????????????????
}