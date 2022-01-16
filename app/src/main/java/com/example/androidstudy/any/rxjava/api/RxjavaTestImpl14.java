package com.example.androidstudy.any.rxjava.api;

import com.example.baselibrary.util.LogUtil;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.rxjava.api
 * ClassName: RxjavaTestImpl14
 * CreateDate: 2022/1/15 10:15 下午
 * Author: zjy
 * Description: 合并数据源
 */
public class RxjavaTestImpl14 implements IRxjavaTest {
    private String result = "数据源来自 = ";
    @Override
    public void run() {
        // merge实现
        Observable<String> network = Observable.just("网络");
        Observable<String> file = Observable.just("本地文件");
        Observable
                .merge(network, file)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        LogUtil.i(TAG, "合并数据源 merge onNext：" + s);
                        result += s + "+";
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        LogUtil.i(TAG, "合并数据源 merge onComplete：" + result);
                    }
                });

        // zip实现
        Observable<String> delay = Observable.just("来自网络的第一个请求").delay(3, TimeUnit.SECONDS);
        Observable<String> delay1 = Observable.just("来自网络的第二个请求").delay(1, TimeUnit.SECONDS);
        Disposable disposable = Observable
                .zip(delay, delay1, new BiFunction<String, String, String>() {
                    @NonNull
                    @Override
                    public String apply(@NonNull String s, @NonNull String s2) throws Exception {
                        return s + " " + s2;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtil.i(TAG, "合并数据源 zip 下游：" + s);
                    }
                });
    }
}
