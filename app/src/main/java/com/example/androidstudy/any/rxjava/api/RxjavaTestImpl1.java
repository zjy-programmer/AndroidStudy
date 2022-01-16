package com.example.androidstudy.any.rxjava.api;

import com.example.baselibrary.util.LogUtil;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.rxjava.api
 * ClassName: RxjavaTestImpl1
 * CreateDate: 2022/1/11 12:24 下午
 * Author: zjy
 * Description:
 */
public class RxjavaTestImpl1 implements IRxjavaTest {
    @Override
    public void run() {
        // 先分步骤写一下
        // 第一步 创建被观察者 上游
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });
        // 第二步 创建观察者 下游
        Observer<Integer> observer = new Observer<Integer>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                LogUtil.i(TAG, "开始订阅了");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogUtil.i(TAG, "next事件：" + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogUtil.i(TAG, "报错了：" + e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtil.i(TAG, "onComplete事件");
            }
        };
        // 第三步 订阅 被观察者(上游)订阅观察者(下游)
        observable.subscribe(observer);

        // 改用链式调用
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("通过链式调用Rxjava");
                emitter.onNext("测试一下");
                emitter.onNext("看看行不行");
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                LogUtil.i(TAG, "开始订阅了");
            }

            @Override
            public void onNext(@NonNull String s) {
                LogUtil.i(TAG, "next事件：" + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogUtil.i(TAG, "报错了：" + e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtil.i(TAG, "onComplete事件");
            }
        });

        // dispose 切断上下游
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onNext(5);
            }
        }).subscribe(new Observer<Integer>() {
            Disposable disposable;
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogUtil.i(TAG, "接收的事件：" + integer);
                if (integer == 3) {
                    if (disposable != null) {
                        disposable.dispose();
                        LogUtil.i(TAG, "切断上下游");
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
