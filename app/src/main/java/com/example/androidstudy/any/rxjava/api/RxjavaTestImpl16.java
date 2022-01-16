package com.example.androidstudy.any.rxjava.api;

import com.example.baselibrary.util.LogUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.rxjava.api
 * ClassName: RxjavaTestImpl16
 * CreateDate: 2022/1/16 10:56 上午
 * Author: zjy
 * Description: 功能防抖
 */
public class RxjavaTestImpl16 implements IRxjavaTest {
    @Override
    public void run() {
        Disposable disposable = Observable
                // 模拟手抖连点
                .interval(300, TimeUnit.MILLISECONDS)
                // 不管点击多少次 每2秒内都只会响应第一次点击
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LogUtil.i(TAG, "功能防抖 下游：" + aLong);
                    }
                });
    }
}
