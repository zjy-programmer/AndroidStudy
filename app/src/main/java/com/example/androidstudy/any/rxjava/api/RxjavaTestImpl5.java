package com.example.androidstudy.any.rxjava.api;

import com.example.androidstudy.any.rxjava.observer.TestObserver;
import com.example.baselibrary.util.LogUtil;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.rxjava.api
 * ClassName: RxjavaTestImpl5
 * CreateDate: 2022/1/14 12:58 下午
 * Author: zjy
 * Description: 组合/合并操作符
 */
public class RxjavaTestImpl5 implements IRxjavaTest {
    @Override
    public void run() {
        // 1. concat / concatArray 组合多个被观察者一起发送数据 合并后 按发送顺序串行执行
        Observable
                // 最多4个
                .concat(Observable.just(1, 2, 3),
                        Observable.just(4, 5, 6),
                        Observable.just(7, 8, 9),
                        Observable.just(10, 11, 12))
                .subscribe(new TestObserver<>("concat"));

        Observable
                // 可以大于4个
                .concatArray(Observable.just(1, 2, 3),
                        Observable.just(4, 5, 6),
                        Observable.just(7, 8, 9),
                        Observable.just(10, 11, 12),
                        Observable.just(13, 14, 15))
                .subscribe(new TestObserver<>("concatArray"));

        // 由于按顺序执行 所以等第一个观察者事件发送完才能发下一个
        Observable.concat(Observable.intervalRange(0, 2, 0, 5, TimeUnit.SECONDS),
                Observable.intervalRange(2, 2, 0, 5, TimeUnit.SECONDS))
                .subscribe(new TestObserver<>("concat"));

        // 2. merge / mergeArray 组合多个被观察者一起发送数据 合并后 按时间线并行执行 由于并行执行 所以观察者收到事件的顺序不是被观察者添加的顺序
        Observable
                // 最多4个
                .merge(Observable.intervalRange(0, 2, 0, 5, TimeUnit.SECONDS),
                        Observable.intervalRange(2, 2, 0, 5, TimeUnit.SECONDS))
                .subscribe(new TestObserver<>("merge"));

        Observable
                // 可以大于4个
                .mergeArray(Observable.intervalRange(0, 2, 0, 5, TimeUnit.SECONDS),
                        Observable.intervalRange(2, 2, 0, 5, TimeUnit.SECONDS),
                        Observable.intervalRange(4, 2, 0, 5, TimeUnit.SECONDS),
                        Observable.intervalRange(6, 2, 0, 5, TimeUnit.SECONDS),
                        Observable.intervalRange(8, 2, 0, 5, TimeUnit.SECONDS),
                        Observable.intervalRange(10, 2, 0, 5, TimeUnit.SECONDS))
                .subscribe(new TestObserver<>("mergeArray"));

        // 3. concatDelayError / mergeDelayError
        // 当使用concat或merge操作符时 如果其中一个被观察者抛出异常 则马上终止其他被观察者发送事件 通过这两个操作符可以等其他被观察者发送事件结束后再抛异常
        Observable.concat(
                Observable.just("a", "b", "c"),
                Observable.error(new Exception("concat 被观察者抛出异常 并且没有使用concatArrayDelayError")),
                Observable.just("d", "e", "f")
        ).subscribe(new TestObserver<>("concat"));

        Observable.concatArrayDelayError(
                Observable.just("a", "b", "c"),
                Observable.error(new Exception("concatArrayDelayError 被观察者抛出异常 并且使用concatArrayDelayError")),
                Observable.just("d", "e", "f")
        ).subscribe(new TestObserver<>("concatArrayDelayError"));

        // 由于并行执行 所以下面这个代码什么都打印不出来 没准多试几次也能打印出来 毕竟是乱序
        Observable
                .merge(Observable.intervalRange(100, 2, 0, 5, TimeUnit.SECONDS),
                        Observable.error(new Exception("merge 被观察者抛出异常 并且没有使用mergeArrayDelayError")),
                        Observable.intervalRange(102, 2, 0, 5, TimeUnit.SECONDS))
                .subscribe(new TestObserver<>("merge"));

        Observable
                .mergeArrayDelayError(Observable.intervalRange(100, 2, 0, 5, TimeUnit.SECONDS),
                        Observable.error(new Exception("mergeArrayDelayError 被观察者抛出异常 并且使用mergeArrayDelayError")),
                        Observable.intervalRange(102, 2, 0, 5, TimeUnit.SECONDS))
                .subscribe(new TestObserver<>("mergeArrayDelayError"));

        // 4. zip 合并多个被观察者发送的事件 生成一个新的事件序列 并发送（严格按照原先事件序列进行对位合并，最终的合并事件数量是多个被观察者中数量最少的数量）
        Observable.zip(
                Observable.just(1, 2, 3, 4, 5),
                Observable.just("a", "b", "c"),
                new BiFunction<Integer, String, String>() {
                    @NonNull
                    @Override
                    public String apply(@NonNull Integer integer, @NonNull String s) throws Exception {
                        return integer + s;
                    }
                }).subscribe(new TestObserver<>("zip"));

        // 设置了线程一样可以
        Observable.zip(
                Observable.just(6, 7, 8, 9, 10).subscribeOn(Schedulers.io()),
                Observable.just("d", "e", "f").subscribeOn(Schedulers.newThread()),
                new BiFunction<Integer, String, String>() {
                    @NonNull
                    @Override
                    public String apply(@NonNull Integer integer, @NonNull String s) throws Exception {
                        return integer + s;
                    }
                }).subscribe(new TestObserver<>("zip"));

        // 5. combineLatest
//        Observable.combineLatest(
//                Observable.just(1, 2),
//                Observable.just(3, 4, 5),
//                Observable.intervalRange(0, 3, 0, 1, TimeUnit.SECONDS),
//                new Function3<Integer, Integer, Long, Long>() {
//                    @NonNull
//                    @Override
//                    public Long apply(@NonNull Integer integer, @NonNull Integer integer2, @NonNull Long aLong) throws Exception {
//                        return integer + integer2 + aLong;
//                    }
//                }
//        ).subscribe(new TestObserver<>("combineLatest"));

        // 等所有被观察者都至少发送一次事件后才会回调apply函数 并且把每一个被观察者当时最新的事件传进来
        Observable.combineLatest(
                Observable.intervalRange(10, 5, 0, 1, TimeUnit.SECONDS),
                Observable.intervalRange(5, 6, 3, 10, TimeUnit.SECONDS),
                new BiFunction<Long, Long, Long>() {
                    @NonNull
                    @Override
                    public Long apply(@NonNull Long aLong, @NonNull Long aLong2) throws Exception {
                        return aLong + aLong2;
                    }
                }
        ).subscribe(new TestObserver<>("combineLatest"));

        // 6. combineLatestDelayError 作用类似于concatDelayError / mergeDelayError
        Observable.combineLatestDelayError(new ObservableSource[]{
                new ObservableSource() {
                    @Override
                    public void subscribe(@NonNull Observer observer) {
                        observer.onNext(1);
                        observer.onError(new Throwable("combineLatestDelayError操作符 抛出异常"));
                        observer.onNext(2);
                        observer.onNext(3);
                    }
                },
                new ObservableSource() {
                    @Override
                    public void subscribe(@NonNull Observer observer) {
                        observer.onNext(1);
                        observer.onNext(2);
                        observer.onNext(3);
                    }
                },
        }, new Function<Object[], Object>() {
            @Override
            public Object apply(@NonNull Object[] objects) throws Exception {
                int r = 0;
                for (Object object : objects) {
                    if (object instanceof Integer) {
                        r += (Integer) object;
                    }
                }
                return r;
            }
        }).subscribe(new TestObserver<>("combineLatestDelayError"));

        // 7. reduce 把被观察者需要发送的事件聚合成1个事件发送
        Disposable subscribe = Observable.just(2, 3, 4, 5)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @NonNull
                    @Override
                    public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                        LogUtil.i(TAG, "reduce 操作符 当前传进来值：" + integer + " 和：" + integer2);
                        return integer * integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtil.i(TAG, "reduce 操作符 下游：" + integer);
                    }
                });

        // 8. collect
        Disposable subscribe1 = Observable.just(1, 2, 3, 4, 5, 6)
                .collect(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        // 这里返回的数据会传到下面的函数中 可以在下面的函数中结合被观察者发的事件做自己的逻辑 当被观察者发完事件下游才会收到事件
                        return 10;
                    }
                }, new BiConsumer<Integer, Integer>() {
                    @Override
                    public void accept(Integer integer, Integer integer2) throws Exception {
                        LogUtil.i(TAG, "collect 操作符：" + integer + " 和：" + integer2);
                    }
                }).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtil.i(TAG, "collect 操作符 下游：" + integer);
                    }
                });

        Disposable subscribe2 = Observable.just(1, 2, 3)
                .collect(new Callable<ArrayList<Integer>>() {
                    @Override
                    public ArrayList<Integer> call() throws Exception {
                        return new ArrayList<>();
                    }
                }, new BiConsumer<ArrayList<Integer>, Integer>() {
                    @Override
                    public void accept(ArrayList<Integer> integers, Integer integer) throws Exception {
                        integers.add(integer);
                    }
                }).subscribe(new BiConsumer<ArrayList<Integer>, Throwable>() {
                    @Override
                    public void accept(ArrayList<Integer> integers, Throwable throwable) throws Exception {
                        LogUtil.i(TAG, "collect 操作符 下游：" + integers);
                    }
                });

        // 9. startWith / startWithArray 在一个被观察者发送事件前 追加发送一些数据 / 一个新的被观察者
        Observable.just(4, 5, 6)
                .startWith(0)
                .startWithArray(1, 2, 3)
                .subscribe(new TestObserver<>("startWith / startWithArray"));

        Observable.just(4,5,6)
                .startWith(Observable.just(0))
                .startWith(Observable.just(1,2,3))
                .subscribe(new TestObserver<>("startWith(Observable observable)"));

        // 10. count 统计被观察者发送事件的数量
        Disposable subscribe3 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        })
                .count()
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LogUtil.i(TAG, "count 操作符 下游：" + aLong);
                    }
                });

    }
}
