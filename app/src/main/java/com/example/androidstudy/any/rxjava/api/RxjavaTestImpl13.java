package com.example.androidstudy.any.rxjava.api;

import com.example.baselibrary.util.LogUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.rxjava.api
 * ClassName: RxjavaTestImpl13
 * CreateDate: 2022/1/14 11:11 下午
 * Author: zjy
 * Description: 网络请求出错重连(结合Retrofit)
 */
public class RxjavaTestImpl13 implements IRxjavaTest {
    // 可重试次数
    private int maxConnectCount = 10;
    // 当前已重试次数
    private int currentRetryCount = 0;
    // 重试等待时间
    private int waitRetryTime = 0;

    @Override
    public void run() {
        Disposable disposable = Observable.just("假装请求网络")
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                                if (throwable instanceof IOException) {
                                    if (currentRetryCount < maxConnectCount) {
                                        currentRetryCount++;
                                        waitRetryTime = 1000 + currentRetryCount * 1000;
                                        return Observable.just(1).delay(waitRetryTime, TimeUnit.SECONDS);
                                    } else {
                                        return Observable.error(new Throwable("重试次数已超过设置次数"));
                                    }
                                } else {
                                    return Observable.error(new Throwable("发生了非IO异常"));
                                }
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtil.i(TAG, "网络请求出错重连 下游：" + s);
                    }
                });

    }
}
