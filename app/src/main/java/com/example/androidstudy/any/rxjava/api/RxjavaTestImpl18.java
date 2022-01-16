package com.example.androidstudy.any.rxjava.api;

import com.example.baselibrary.util.LogUtil;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.rxjava.api
 * ClassName: RxjavaTestImpl18
 * CreateDate: 2022/1/16 11:24 上午
 * Author: zjy
 * Description: 联合判断
 */
public class RxjavaTestImpl18 implements IRxjavaTest {
    @Override
    public void run() {
        // 输入姓名
        Observable<String> name = Observable.just("姓名").delay(3, TimeUnit.SECONDS);
        // 输入年龄
        Observable<String> age = Observable.just("年龄").delay(1, TimeUnit.SECONDS);
        // 输入职业
        Observable<String> job = Observable.just("职业").delay(2, TimeUnit.SECONDS);

        Disposable disposable = Observable.combineLatest(name, age, job, new Function3<String, String, String, String>() {
            @NonNull
            @Override
            public String apply(@NonNull String s, @NonNull String s2, @NonNull String s3) throws Exception {
                LogUtil.i(TAG, "联合判断 combineLatest：" + s + " " + s2 + " " + s3);
                return s + " " + s2 + " " + s3;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtil.i(TAG, "联合判断 下游：" + s);
                    }
                });
    }
}
