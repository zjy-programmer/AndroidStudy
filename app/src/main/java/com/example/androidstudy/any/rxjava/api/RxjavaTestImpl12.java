package com.example.androidstudy.any.rxjava.api;

import com.example.baselibrary.util.LogUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.rxjava.api
 * ClassName: RxjavaTestImpl12
 * CreateDate: 2022/1/14 11:01 下午
 * Author: zjy
 * Description: 无条件的轮询
 */
public class RxjavaTestImpl12 implements IRxjavaTest {
    @Override
    public void run() {
        // 无限轮询
        Disposable disposable = Observable.interval(2, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LogUtil.i(TAG, "轮询");
                    }
                }).subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                    }
                });

        Disposable disposable1 = Observable.interval(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LogUtil.i(TAG, "轮询 放这也行");
                    }
                });


        // 有限次轮询
        Disposable disposable2 = Observable.intervalRange(0, 5, 0, 2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LogUtil.i(TAG, "有限次轮询");
                    }
                });
    }
}
