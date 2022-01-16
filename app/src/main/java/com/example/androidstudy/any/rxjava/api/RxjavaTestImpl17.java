package com.example.androidstudy.any.rxjava.api;

import com.example.baselibrary.util.LogUtil;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.rxjava.api
 * ClassName: RxjavaTestImpl17
 * CreateDate: 2022/1/16 11:04 上午
 * Author: zjy
 * Description: 从磁盘 / 内存缓存中获取缓存数据
 */
public class RxjavaTestImpl17 implements IRxjavaTest {
    private String memoryCache = null;
    private String diskCache = "从磁盘缓存中获取数据";

    @Override
    public void run() {
        // 检查内存缓存是否有该数据的缓存
        Observable<String> memory = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                if (memoryCache != null) {
                    emitter.onNext(memoryCache);
                } else {
                    emitter.onComplete();
                }
            }
        });

        // 检查磁盘缓存是否有该数据的缓存
        Observable<String> disk = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                if (diskCache != null) {
                    emitter.onNext(diskCache);
                } else {
                    emitter.onComplete();
                }
            }
        });

        // 通过网络获取数据
        Observable<String> network = Observable.just("从网络中获取数据");

        Disposable disposable = Observable
                .concat(memory, disk, network)
                .firstElement()
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtil.i(TAG, "从磁盘 / 内存缓存中获取缓存数据 下游 最终从：" + s + " 获取到数据");
                    }
                });
    }
}
