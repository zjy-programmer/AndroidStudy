package com.example.androidstudy.any.rxjava.api;

import com.example.androidstudy.any.rxjava.observer.TestObserver;
import com.example.baselibrary.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.rxjava.api
 * ClassName: RxjavaTestImpl6
 * CreateDate: 2022/1/14 3:54 下午
 * Author: zjy
 * Description: 变换操作符
 */
public class RxjavaTestImpl6 implements IRxjavaTest {
    @Override
    public void run() {
        // 1. map 被观察者发送的每一个事件都通过指定的函数处理
        Observable.just(1, 2, 3)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(@NonNull Integer integer) throws Exception {
//                        return integer + "";
                        return integer * integer + "";
                    }
                }).subscribe(new TestObserver<>("map"));

        // 2. flatMap 将被观察者发送的事件序列进行拆分、单独转换 再合并成一个新的事件序列发送 新合并生成的事件序列顺序是无序的 即 与旧序列发送事件的顺序无关
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        })
                .flatMap(new Function<Integer, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            list.add(integer + " " + i + " 函数方法");
                        }
                        return Observable.fromIterable(list);
                    }
                }).subscribe(new TestObserver<>("flatMap"));

        // 3. concatMap 与flatMap类似 区别在于新生成的事件序列顺序与旧序列顺序一致
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        })
                .concatMap(new Function<Integer, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            list.add(integer + " " + i + " 函数方法");
                        }
                        return Observable.fromIterable(list);
                    }
                }).subscribe(new TestObserver<>("concatMap"));

        // 4. buffer
        Observable.just(1, 2, 3, 4, 5)
                // 每次缓存3个 发送3个 因为skip(步长)为1 则走一步再接着缓存3个 然后发3个
                .buffer(3, 1)
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> stringList) {
                        //
                        LogUtil.i(TAG, " 缓存区里的事件数量 = " + stringList.size());
                        for (Integer value : stringList) {
                            LogUtil.i(TAG, " 事件 = " + value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.i(TAG, "对Complete事件作出响应");
                    }
                });
    }
}
