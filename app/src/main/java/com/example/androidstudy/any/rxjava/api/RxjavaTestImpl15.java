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
 * ClassName: RxjavaTestImpl15
 * CreateDate: 2022/1/16 10:43 上午
 * Author: zjy
 * Description: 联想搜索优化
 */
public class RxjavaTestImpl15 implements IRxjavaTest {
    @Override
    public void run() {
        Disposable disposable = Observable
                .intervalRange(0, 10, 0, 600, TimeUnit.MILLISECONDS)
                .debounce(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LogUtil.i(TAG, "联想搜索优化 下游：" + aLong);
                    }
                });

    }
}
