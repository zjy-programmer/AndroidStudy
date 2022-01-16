package com.example.androidstudy.any.rxjava.api;

import com.example.androidstudy.any.rxjava.observer.TestObserver;
import com.example.baselibrary.util.LogUtil;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.rxjava.api
 * ClassName: RxjavaTestImpl4
 * CreateDate: 2022/1/13 9:09 下午
 * Author: zjy
 * Description: 过滤操作符
 */
public class RxjavaTestImpl4 implements IRxjavaTest {
    @Override
    public void run() {
        // 1. filter
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onNext(5);
                emitter.onComplete();
            }
        })
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer integer) throws Exception {
                        // 返回true 要这个数 返回false 不要这个数
                        if ((integer & 1) == 1) {
                            return true;
                        }
                        return false;
                    }
                }).subscribe(new TestObserver<>("filter"));

        // 2. ofType 过滤特定数据类型的数据
        Observable.just(1, "car", 3, "play", 5)
                // 不要Integer类型的数据
                .ofType(Integer.class)
                .subscribe(new TestObserver<>("ofType"));

        // 3. skip / skipLast 跳过某个事件
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8)
                // 跳过正数前3项
                .skip(3)
                // 跳过倒数后2项
                .skipLast(2)
                .subscribe(new TestObserver<>("skip / skipLast"));

        Observable.intervalRange(0, 5, 0, 1, TimeUnit.SECONDS)
                // 跳过第1秒发送的数据
                .skip(1, TimeUnit.SECONDS)
                // 跳过最后1秒发送的数据
                .skipLast(1, TimeUnit.SECONDS)
                .subscribe(new TestObserver<>("skip(time, timeUtil) / skipLast(time, timeUtil)"));

        // 4. distinct / distinctUntilChanged
        Observable.just(1, 2, 3, 1, 2)
                // 过滤事件序列中重复的事件
                .distinct()
                .subscribe(new TestObserver<>("distinct"));

        Observable.just(1, 2, 3, 1, 2, 3, 3, 4, 4)
                // 过滤事件序列连续重复的事件
                .distinctUntilChanged()
                .subscribe(new TestObserver<>("distinctUntilChanged"));

        // 5. take
        Observable.just(1, 2, 3, 4, 5)
                // 只接收2个事件
                .take(2)
                .subscribe(new TestObserver<>("take"));

        // 6. takeLast
        Observable.just(1,2,3,4,5)
                // 只接收后2两个事件
                .takeLast(2)
                .subscribe(new TestObserver<>("takeLast"));

//        // 7. throttleFirst
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                // 隔段事件发送时间
//                e.onNext(1);
//                Thread.sleep(500);
//
//                e.onNext(2);
//                Thread.sleep(400);
//
//                e.onNext(3);
//                Thread.sleep(300);
//
//                e.onNext(4);
//                Thread.sleep(300);
//
//                e.onNext(5);
//                Thread.sleep(300);
//
//                e.onNext(6);
//                Thread.sleep(400);
//
//                e.onNext(7);
//                Thread.sleep(300);
//                e.onNext(8);
//
//                Thread.sleep(300);
//                e.onNext(9);
//
//                Thread.sleep(300);
//                e.onComplete();
//            }
//        })
//                // 每1秒采集数据并只发送第一个数据
//                .throttleFirst(1, TimeUnit.SECONDS)
//                .subscribe(new TestObserver<>("throttleFirst"));
//
//        // 7. throttleLast
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                // 隔段事件发送时间
//                e.onNext(1);
//                Thread.sleep(500);
//
//                e.onNext(2);
//                Thread.sleep(400);
//
//                e.onNext(3);
//                Thread.sleep(300);
//
//                e.onNext(4);
//                Thread.sleep(300);
//
//                e.onNext(5);
//                Thread.sleep(300);
//
//                e.onNext(6);
//                Thread.sleep(400);
//
//                e.onNext(7);
//                Thread.sleep(300);
//                e.onNext(8);
//
//                Thread.sleep(300);
//                e.onNext(9);
//
//                Thread.sleep(300);
//                e.onComplete();
//            }
//        })
//                // 每1秒采集数据并只发送最后一个数据
//                .throttleLast(1, TimeUnit.SECONDS)
//                .subscribe(new TestObserver<>("throttleLast"));
//
//        // 8. sample 和throttleLast类似
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                // 隔段事件发送时间
//                e.onNext(1);
//                Thread.sleep(500);
//
//                e.onNext(2);
//                Thread.sleep(400);
//
//                e.onNext(3);
//                Thread.sleep(300);
//
//                e.onNext(4);
//                Thread.sleep(300);
//
//                e.onNext(5);
//                Thread.sleep(300);
//
//                e.onNext(6);
//                Thread.sleep(400);
//
//                e.onNext(7);
//                Thread.sleep(300);
//                e.onNext(8);
//
//                Thread.sleep(300);
//                e.onNext(9);
//
//                Thread.sleep(300);
//                e.onComplete();
//            }
//        })
//                .sample(1, TimeUnit.SECONDS)
//                .subscribe(new TestObserver<>("sample"));
//
//        // 9. throttleWithTimeout / debounce
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                // 隔段事件发送时间
//                e.onNext(1);
//                Thread.sleep(500);
//                e.onNext(2); // 1和2之间的间隔小于指定时间1s，所以前1次数据（1）会被抛弃，2会被保留
//                Thread.sleep(1500);  // 因为2和3之间的间隔大于指定时间1s，所以之前被保留的2事件将发出
//                e.onNext(3);
//                Thread.sleep(1500);  // 因为3和4之间的间隔大于指定时间1s，所以3事件将发出
//                e.onNext(4);
//                Thread.sleep(500); // 因为4和5之间的间隔小于指定时间1s，所以前1次数据（4）会被抛弃，5会被保留
//                e.onNext(5);
//                Thread.sleep(500); // 因为5和6之间的间隔小于指定时间1s，所以前1次数据（5）会被抛弃，6会被保留
//                e.onNext(6);
//                Thread.sleep(1500); // 因为6和Complete实践之间的间隔大于指定时间1s，所以之前被保留的6事件将发出
//
//                e.onComplete();
//            }
//        })
//                .throttleWithTimeout(1, TimeUnit.SECONDS)
//                .subscribe(new TestObserver<>("throttleWithTimeout"));
//
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                // 隔段事件发送时间
//                e.onNext(1);
//                Thread.sleep(500);
//                e.onNext(2); // 1和2之间的间隔小于指定时间1s，所以前1次数据（1）会被抛弃，2会被保留
//                Thread.sleep(1500);  // 因为2和3之间的间隔大于指定时间1s，所以之前被保留的2事件将发出
//                e.onNext(3);
//                Thread.sleep(1500);  // 因为3和4之间的间隔大于指定时间1s，所以3事件将发出
//                e.onNext(4);
//                Thread.sleep(500); // 因为4和5之间的间隔小于指定时间1s，所以前1次数据（4）会被抛弃，5会被保留
//                e.onNext(5);
//                Thread.sleep(500); // 因为5和6之间的间隔小于指定时间1s，所以前1次数据（5）会被抛弃，6会被保留
//                e.onNext(6);
//                Thread.sleep(1500); // 因为6和Complete实践之间的间隔大于指定时间1s，所以之前被保留的6事件将发出
//
//                e.onComplete();
//            }
//        })
//                .debounce(1, TimeUnit.SECONDS)
//                .subscribe(new TestObserver<>("debounce"));

        // 10. firstElement / lastElement
        Disposable disposable = Observable.just(1, 2, 3, 4, 5)
                // 只取第一个有效事件(next事件)
                .firstElement()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtil.i(TAG, "firstElement操作符 下游：" + integer);
                    }
                });

        Disposable disposable1 = Observable.just(1, 2, 3, 4, 5)
                // 只取最后一个元素
                .lastElement()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtil.i(TAG, "lastElement操作符 下游：" + integer);
                    }
                });

        // 11. elementAt 通过索引获取事件 可以越界
        Disposable disposable2 = Observable.just(1, 2, 3, 4, 5)
                .elementAt(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtil.i(TAG, "elementAt操作符 下游：" + integer);
                    }
                });

        Disposable disposable3 = Observable.just(1, 2, 3, 4, 5)
                .elementAt(6, 10)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtil.i(TAG, "elementAt操作符 越界 发送自己设置的默认值 下游：" + integer);
                    }
                });

        // 12. elementAtOrError
        Disposable disposable4 = Observable.just(1, 2, 3, 4, 5)
                .elementAtOrError(6)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtil.i(TAG, "elementAtOrError操作符 越界 直接报错 下游：" + integer);
                    }
                });
    }
}
