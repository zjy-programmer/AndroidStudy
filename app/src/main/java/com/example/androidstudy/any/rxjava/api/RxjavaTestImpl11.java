package com.example.androidstudy.any.rxjava.api;

import com.example.baselibrary.util.LogUtil;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.rxjava.api
 * ClassName: RxjavaTestImpl11
 * CreateDate: 2022/1/14 10:48 下午
 * Author: zjy
 * Description: 有条件的轮询
 */
public class RxjavaTestImpl11 implements IRxjavaTest {
    private int i = 0;

    @Override
    public void run() {

        Disposable disposable = Observable.just(1)
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                        return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(@NonNull Object o) throws Exception {
                                if (i > 3) {
                                    return Observable.error(new Throwable("轮询结束"));
                                }
                                LogUtil.i(TAG, "当前轮询次数：" + i);
                                return Observable.just(1).delay(2, TimeUnit.SECONDS);
                            }
                        });
                    }
                }).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        i++;
                        LogUtil.i(TAG, "下游：" + integer);
                    }
                });
    }
}
