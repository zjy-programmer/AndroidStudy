package com.example.androidstudy.any.rxjava.api;

import com.example.androidstudy.any.rxjava.observer.TestObserver;
import com.example.baselibrary.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.rxjava.api
 * ClassName: RxjavaTestImpl7
 * CreateDate: 2022/1/14 4:38 下午
 * Author: zjy
 * Description: 条件/布尔操作符
 */
public class RxjavaTestImpl7 implements IRxjavaTest {
    @Override
    public void run() {
        // 1. all
        Disposable disposable = Observable.just(1, 2, 3, 4, 5, 6)
                // 判断是否所有发送的事件都满足条件 只要有一个不满足 被观察者立马停止判断 并给观察者发事件 或者都满足条件后再给观察者发消息 观察者只会收到一次消息
                .all(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer integer) throws Exception {
                        LogUtil.i(TAG, "all 操作符 条件判断 当前值：" + integer);
                        return integer < 4;
                    }
                }).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        LogUtil.i(TAG, "all 操作符 下游：" + aBoolean);
                    }
                });

        // 2. takeWhile
        Observable.just(1,2,3,4,5,6)
                // 每次都会给观察者发消息 但是必须满足判断条件
                .takeWhile(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer integer) throws Exception {
                        return integer < 4;
                    }
                }).subscribe(new TestObserver<>("takeWhile"));

        // 3. skipWhile
        Observable.just(1,2,3,4,5,6)
                // 跟takeWhile正相反 满足条件会跳过
                .skipWhile(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer integer) throws Exception {
                        return integer < 4;
                    }
                }).subscribe(new TestObserver<>("skipWhile"));

        // 4. takeUntil
        Observable.just(1,2,3,4,5,6)
                // 执行到某个条件时被观察者停止发送事件 先给下游发 然后再条件判断 所以下游会收到5
                .takeUntil(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer integer) throws Exception {
                        LogUtil.i(TAG, "takeUntil 操作符 条件判断 当前值：" + integer);
                        // 当发送的数大于4了 就停止发送
                        return integer > 4;
                    }
                }).subscribe(new TestObserver<>("takeUntil"));

        Observable.interval(1, TimeUnit.SECONDS)
                // 当作为条件判断的被观察者开始发送数据 原本的被观察者就停止发送数据
                .takeUntil(Observable.timer(5, TimeUnit.SECONDS))
                .subscribe(new TestObserver<>("takeUntil"));

        // 5. skipUntil
        Observable.interval(1, TimeUnit.SECONDS)
                // 当作为条件判断的被观察者开始发送数据 原本的被观察者才开始给观察者发送数据 但是被观察者一开始就已经启动了的
                .skipUntil(Observable.timer(5, TimeUnit.SECONDS))
                .subscribe(new TestObserver<>("skipUntil"));

        // 6. sequenceEqual
        Disposable subscribe = Observable.sequenceEqual(
                Observable.just(4, 5, 6),
                Observable.just(4, 5, 6),
                // 可以自己设置判断条件 也可以不设置
                new BiPredicate<Integer, Integer>() {
                    @Override
                    public boolean test(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                        return integer.equals(integer2);
                    }
                })
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        LogUtil.i(TAG, "sequenceEqual 操作符 下游 判断两个被观察者发送的事件是否一样：" + aBoolean);
                    }
                });

        // 7. contains
        Disposable subscribe1 = Observable.just(1, 2, 3, 4, 5, 6)
                .contains(3)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        LogUtil.i(TAG, "contains 操作符 下游 判断发送的数据中是否有指定的数据：" + aBoolean);
                    }
                });

        // 8. isEmpty
        Disposable subscribe2 = Observable.just(1, 2, 3, 4, 5, 6)
                .isEmpty()
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        LogUtil.i(TAG, "isEmpty 操作符 下游 判断发送的数据是否为空：" + aBoolean);
                    }
                });

        // 9. amb
        List<ObservableSource<Integer>> list = new ArrayList<>();
        list.add(Observable.just(1,2,3).delay(1, TimeUnit.SECONDS));
        list.add(Observable.just(4,5,6));
        Observable
                // 只使用先发送数据的被观察者 其他的被观察者丢弃
                .amb(list)
                .subscribe(new TestObserver<>("amb"));

        // 10. defaultIfEmpty
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(1);
                emitter.onComplete();
//                emitter.onError(new Throwable("defaultIfEmpty 操作符 假装抛出异常"));
            }
        })
                // 如果只发送onComplete事件 那么会给观察者发送onNext(10)这个事件
                .defaultIfEmpty(10)
                .subscribe(new TestObserver<>("defaultIfEmpty"));
    }
}
