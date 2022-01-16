package com.example.androidstudy.any.rxjava.api;

import android.util.Log;

import com.example.baselibrary.util.LogUtil;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.rxjava.api
 * ClassName: RxjavaTestImpl9
 * CreateDate: 2022/1/14 7:16 下午
 * Author: zjy
 * Description: 背压
 */
public class RxjavaTestImpl9 implements IRxjavaTest {
    @Override
    public void run() {
        // 1. 背压简单使用
        // 背压的被观察者
        Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR);

        // 背压的观察者
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {

            @Override
            public void onSubscribe(Subscription subscription) {
                // Subscription 和 Disposable具有相同的切断连接功能 同时还多了request功能
                // 这个不写下游收不事件
                subscription.request(100);
            }

            @Override
            public void onNext(Integer integer) {
                LogUtil.i(TAG, "Subscriber 背压下游 onNext：" + integer);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        };

        flowable.subscribe(subscriber);

        // 2. 控制观察者接收事件的速度 缓存区默认128 超过会报错
        // 异步的情况
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
                // 一共发送4个事件 直接放到缓存区
                LogUtil.i(TAG, "发送事件 1");
                emitter.onNext(1);
                LogUtil.i(TAG, "发送事件 2");
                emitter.onNext(2);
                LogUtil.i(TAG, "发送事件 3");
                emitter.onNext(3);
                LogUtil.i(TAG, "发送事件 4");
                emitter.onNext(4);
                LogUtil.i(TAG, "发送完成");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        // 作用：决定观察者能够接收多少个事件
                        // 如设置了subscription.request(3)，这就说明观察者能够接收3个事件（多出的事件存放在缓存区）
                        // 官方默认推荐使用Long.MAX_VALUE，即subscription.request(Long.MAX_VALUE);
                        subscription.request(3);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtil.i(TAG, "控制观察者接收事件的速度 接收的数：" + integer);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // 同步的情况(即被观察者和观察者在同一个线程) 同步是不会放在缓存区的
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
                LogUtil.i(TAG, "同步的情况 发送事件 1");
                emitter.onNext(1);
                LogUtil.i(TAG, "同步的情况 发送事件 2");
                emitter.onNext(2);
                LogUtil.i(TAG, "同步的情况 发送事件 3");
                emitter.onNext(3);
                LogUtil.i(TAG, "同步的情况 发送事件 4");
                emitter.onNext(4);
                LogUtil.i(TAG, "同步的情况 发送完成");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        // 同步时 被观察者发一个 观察者收一个 不会出现被观察者发送事件的速度大于观察者接收事件的速度
//                        subscription.request(4);
                        // 但是 会出现被观察者发送事件的个数大于观察者接收事件个数的问题 这个问题会导致抛出异常
                        subscription.request(3);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtil.i(TAG, "同步的情况 控制观察者接收事件的速度 接收的数：" + integer);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        LogUtil.e(TAG, "同步的情况 控制观察者接收事件的速度 onError：" + throwable);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // 3. 控制被观察者发送事件的速度
        // 同步情况
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
                long n = emitter.requested();

                LogUtil.i(TAG, "观察者可接收事件数：" + n);
                // 根据emitter.requested()的值，即当前观察者需要接收的事件数量来发送事件
                for (int i = 0; i < n; i++) {
                    emitter.onNext(i);
                }
            }
        }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(10);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtil.i(TAG, "控制被观察者发送事件的速度 同步情况 onNext：" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // 同步情况的特性
        // 可叠加性 观察者可连续接收事件 被观察者会进行叠加并一起发送
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
                long n = emitter.requested();
                LogUtil.i(TAG, "可叠加性 观察者可接收事件数：" + n);
            }
        }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(10);
                        s.request(20);
                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // 实时更新性 每次发送事件后 emitter.requested()会实时更新观察者能接收的事件数 仅计算onNext事件 complete和error不算
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
                long n = emitter.requested();
                LogUtil.i(TAG, "实时更新性 观察者可接收的事件数：" + n);
                emitter.onNext(1);
                n = emitter.requested();
                LogUtil.i(TAG, "实时更新性 观察者可接收的事件数：" + n);
                emitter.onNext(2);
                n = emitter.requested();
                LogUtil.i(TAG, "实时更新性 观察者可接收的事件数：" + n);
                emitter.onNext(3);
                n = emitter.requested();
                LogUtil.i(TAG, "实时更新性 观察者可接收的事件数：" + n);
            }
        }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(10);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtil.i(TAG, "实时更新性 onNext：" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // 异常 当emitter.requested()减到0 表示观察者不可接收事件 如果被观察者继续发 则会抛异常
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
                long n = emitter.requested();
                LogUtil.i(TAG, "异常 观察者可接收的事件数：" + n);
                emitter.onNext(1);
                n = emitter.requested();
                LogUtil.i(TAG, "异常 观察者可接收的事件数：" + n);
                emitter.onNext(2);
                n = emitter.requested();
                LogUtil.i(TAG, "异常 观察者可接收的事件数：" + n);
                emitter.onNext(3);
                n = emitter.requested();
                LogUtil.i(TAG, "异常 观察者可接收的事件数：" + n);
            }
        }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        // 当观察者没有调用request方法时 等于request(0)
                        s.request(2);
                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtil.e(TAG, "异常 onError：" + t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // 异步情况 由于处于不同线程 被观察者不能根据观察者接收事件的能力控制发送事件的速度
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
                LogUtil.i(TAG, "异步 观察者可接收事件的数量：" + emitter.requested());
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        // 这个值只会影响观察者的request 不会影响被观察的request
                        s.request(150);
                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // 通过被观察者内部固定调用被观察者线程中的request(n)从而反向控制被观察者的发送事件速度
//        final Subscription[] subscription = new Subscription[1];
//        Flowable.create(new FlowableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
//                LogUtil.i(TAG, "观察者可接收事件数量 = " + emitter.requested());
//                boolean flag = false;
//                for (int i = 0; i < 500; i++) {
//                    while (emitter.requested() == 0) {
//                        if (!flag) {
//                            flag = true;
//                        }
//                    }
//                    LogUtil.i(TAG, "发送了事件" + i + "，观察者可接收事件数量 = " + emitter.requested());
//                    emitter.onNext(i);
//                }
//            }
//        }, BackpressureStrategy.ERROR)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Integer>() {
//                    @Override
//                    public void onSubscribe(Subscription s) {
//                        subscription[0] = s;
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        LogUtil.i(TAG, "异步 onNext：" + integer);
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//        subscription[0].request(48);
//        subscription[0].request(48);
//        subscription[0].request(48);
//        subscription[0].request(48);

        // 4. 背压模式
        // BackpressureStrategy.ERROR 抛MissingBackpressureException异常
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
                // 发送 129个事件
                for (int i = 0; i < 129; i++) {
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(150);
                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtil.e(TAG, "BackpressureStrategy.ERROR：" + t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // BackpressureStrategy.MISSING 抛MissingBackpressureException异常
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
                // 发送 129个事件
                for (int i = 0; i < 129; i++) {
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.MISSING)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(150);
                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtil.e(TAG, "BackpressureStrategy.MISSING：" + t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // BackpressureStrategy.BUFFER 将缓存区大小设置成无限大 要小心OOM
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
                // 发送 139个事件
                for (int i = 0; i < 139; i++) {
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(150);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        LogUtil.i(TAG, "BackpressureStrategy.BUFFER onNext：" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtil.e(TAG, "BackpressureStrategy.BUFFER：" + t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // BackpressureStrategy.DROP 超过缓存大小（128）的事件丢弃
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
                // 发送 129个事件
                for (int i = 0; i < 129; i++) {
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.DROP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(150);
                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtil.e(TAG, "BackpressureStrategy.DROP：" + t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // BackpressureStrategy.LATEST 只保存最新的和缓存区（128）的数据 比如发送150个数据 缓存区只保留 第1到第128和第150事件
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Exception {
                // 发送 129个事件
                for (int i = 0; i < 129; i++) {
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(150);
                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtil.e(TAG, "BackpressureStrategy.DROP：" + t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // 5. 背压策略模式方法 Flowable.create 可以选择背压模式 但是通过其他操作符创建的Flowable没法传入背压模式参数 所以要使用背压策略模式方法 默认采用BackpressureStrategy.ERROR模式
        Flowable.interval(1, TimeUnit.SECONDS)
                .onBackpressureBuffer()
//                .onBackpressureDrop()
//                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
