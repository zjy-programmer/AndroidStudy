package com.example.androidstudy.any.rxjava.api;

import com.example.androidstudy.any.rxjava.observer.TestObserver;
import com.example.baselibrary.util.LogUtil;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.rxjava.api
 * ClassName: RxjavaTestImpl3
 * CreateDate: 2022/1/13 4:58 下午
 * Author: zjy
 * Description: 功能型操作符
 */
public class RxjavaTestImpl3 implements IRxjavaTest {
    @Override
    public void run() {
        // 1. subscribe
        Disposable subscribe = Observable.just(1).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtil.i(TAG, "subscribe操作符：" + integer);
            }
        });

        // 2. delay
        Disposable subscribe1 = Observable.just(1, 2, 3)
                .delay(3, TimeUnit.SECONDS)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtil.i(TAG, "delay操作符 延迟3秒发送事件：" + integer);
                    }
                });

        // 3. do系列
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
//                emitter.onComplete();
                emitter.onError(new Throwable("假装发生了错误"));
            }
        }).doOnEach(new Consumer<Notification<Integer>>() {
            @Override
            public void accept(Notification<Integer> integerNotification) throws Exception {
                // onNext onError都是发送数据事件
                LogUtil.i(TAG, "doOnEach 当Observable每发送一次数据事件就会调用一次：" + integerNotification.getValue());
            }
        }).doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtil.i(TAG, "doOnNext 执行onNext事件前调用：" + integer);
            }
        }).doAfterNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                LogUtil.i(TAG, "doAfterNext 执行onNext事件后调用：" + integer);
            }
        }).doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                LogUtil.i(TAG, "doOnComplete Observable正常发送事件完毕后调用 在onComplete之前调用");
            }
        }).doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LogUtil.i(TAG, "doOnError Observable发送错误事件调用 在onError之前调用");
            }
        }).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                LogUtil.i(TAG, "doOnSubscribe Observable订阅时调用 在onSubscribe之前调用");
            }
        }).doAfterTerminate(new Action() {
            @Override
            public void run() throws Exception {
                LogUtil.i(TAG, "doAfterTerminate Observable发送事件完毕后调用 无论正常发送完毕 / 异常终止");
            }
        }).doFinally(new Action() {
            @Override
            public void run() throws Exception {
                LogUtil.i(TAG, "doFinally 最后执行 在doAfterTerminate之前执行");
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                LogUtil.i(TAG, "do系列操作符 onSubscribe");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogUtil.i(TAG, "do系列操作符 onNext：" + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogUtil.i(TAG, "do系列操作符 onError：" + e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtil.i(TAG, "do系列操作符 onComplete");
            }
        });

        // 4. onErrorReturn
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onError(new Throwable("假装发生了错误"));
            }
        }).onErrorReturn(new Function<Throwable, Integer>() {
            @Override
            public Integer apply(@NonNull Throwable throwable) throws Exception {
                LogUtil.i(TAG, "在onErrorReturn处理了错误");
                // 这里返回666 所以下游的onError不会调用 onNext会收到666这个事件
                return 666;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogUtil.i(TAG, "onErrorReturn操作符：" + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogUtil.i(TAG, "onErrorReturn操作符：" + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

        // 5. onErrorResumeNext
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                // 下面两种都会调用onErrorResumeNext方法 不会调用下游的onError
//                emitter.onError(new Throwable("onErrorResumeNext操作符：假装发生了错误"));
                emitter.onError(new Exception("onErrorResumeNext操作符：假装发生了错误"));
            }
        }).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> apply(@NonNull Throwable throwable) throws Exception {
                // 这里处理错误 下游的onError方法不会回调
                return Observable.just(11, 22);
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogUtil.i(TAG, "onErrorResumeNext操作符 onNext：" + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogUtil.i(TAG, "onErrorResumeNext操作符 onError：" + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

        // 6. onExceptionResumeNext
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                // 这样不会走onExceptionResumeNext方法 而是会走下游的onError
//                emitter.onError(new Throwable("onExceptionResumeNext操作符：假装发生了错误"));
                // 这样才会走onExceptionResumeNext方法
                emitter.onError(new Exception("onExceptionResumeNext操作符：假装发生了错误"));
            }
        }).onExceptionResumeNext(new Observable<Integer>() {
            @Override
            protected void subscribeActual(Observer<? super Integer> observer) {
                observer.onNext(33);
                observer.onNext(44);
                observer.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogUtil.i(TAG, "onExceptionResumeNext操作符 onNext：" + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogUtil.i(TAG, "onExceptionResumeNext操作符 onError：" + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

        // 7. retry 出现错误时 让被观察者重新发送数据 一直错误就一直发
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(1);
//                emitter.onNext(2);
//                emitter.onError(new Exception("retry操作符 假装发生了错误"));
//                emitter.onNext(3);
//            }
//        })
//                .retry()
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@NonNull Integer integer) {
//                        LogUtil.i(TAG, "retry操作符 onNext：" + integer);
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        LogUtil.i(TAG, "retry操作符 onError：" + e.getMessage());
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        LogUtil.i(TAG, "retry操作符 onComplete");
//                    }
//                });

        // 8. retry(long times) 出现错误时 让被观察者重新发送times次数据
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new Exception("retry(long times)操作符 假装发生了错误"));
                emitter.onNext(3);
            }
        })
                .retry(3)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        LogUtil.i(TAG, "retry(long times)操作符 onNext：" + integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtil.i(TAG, "retry(long times)操作符 onError：" + e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        LogUtil.i(TAG, "retry(long times)操作符 onComplete");
                    }
                });

        // 9. retry(Predicate predicate) 出现错误时 判断是否需要重新发送数据
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new Exception("retry(Predicate predicate)操作符 假装发生了错误"));
                emitter.onNext(3);
            }
        })
                .retry(new Predicate<Throwable>() {
                    @Override
                    public boolean test(@NonNull Throwable throwable) throws Exception {
                        LogUtil.i(TAG, "retry(Predicate predicate)操作符 获得异常 判断是否重试 返回true 重新发送若持续遇到错误就持续重新发送 返回false 不重新发送数据");
                        return false;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        LogUtil.i(TAG, "retry(Predicate predicate)操作符 onNext：" + integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtil.i(TAG, "retry(Predicate predicate)操作符 onError：" + e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        LogUtil.i(TAG, "retry(Predicate predicate)操作符 onComplete");
                    }
                });

        // 10. retry(new BiPredicate<Integer, Throwable>)
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new Exception("retry(new BiPredicate<Integer, Throwable>)操作符 假装发生了错误"));
                emitter.onNext(3);
            }
        })
                .retry(new BiPredicate<Integer, Throwable>() {
                    @Override
                    public boolean test(@NonNull Integer integer, @NonNull Throwable throwable) throws Exception {
                        // integer是当前重试次数 从1开始
                        if (integer == 2) {
                            return false;
                        }
                        LogUtil.i(TAG, "retry(new BiPredicate<Integer, Throwable>)操作符 获得异常 判断是否重试 返回true 重新发送若持续遇到错误就持续重新发送 返回false 不重新发送数据：" + integer);
                        return true;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        LogUtil.i(TAG, "retry(new BiPredicate<Integer, Throwable>)操作符 onNext：" + integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtil.i(TAG, "retry(new BiPredicate<Integer, Throwable>)操作符 onError：" + e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        LogUtil.i(TAG, "retry(new BiPredicate<Integer, Throwable>)操作符 onComplete");
                    }
                });

        // 11. retry(long times, new BiPredicate<Integer, Throwable>)
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new Exception("retry(long times, new BiPredicate<Integer, Throwable>)操作符 假装发生了错误"));
                emitter.onNext(3);
            }
        })
                .retry(3, new Predicate<Throwable>() {
                    @Override
                    public boolean test(@NonNull Throwable throwable) throws Exception {
                        LogUtil.i(TAG, "retry(long times, new BiPredicate<Integer, Throwable>)操作符 获得异常 判断是否重试 返回true 重新发送最多重试times次 返回false 不重新发送数据");
                        return true;
                    }
                })
                .subscribe(new TestObserver<>("retry(long times, new BiPredicate<Integer, Throwable>)"));

        // 12. retryUntil
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new Exception("retryUntil操作符 假装发生了错误"));
                emitter.onNext(3);
            }
        })
                .retryUntil(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() throws Exception {
                        // 返回false 一直重新发送数据 返回true 不重新发送
                        return true;
                    }
                })
                .subscribe(new TestObserver<>("retryUntil"));

        // 13. retryWhen
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new Throwable("retryWhen操作符 假装发生了错误"));
                emitter.onNext(3);
            }
        })
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
                        // 这么写一次都不发 因为error只发送onError事件 所以下游也只会收到onError事件
//                        return Observable.error(new Exception("retryWhen操作符 终止了"));
                        // 这么写会重新发两次 因为just发送了两次 并且由于just会再发一个complete 所以下游还会接收到complete事件
//                        return Observable.just(66, 77);

                        // TODO: 2022/1/13 下面这么写的结果为什么会这样 有时间再看看
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                                // 1. 若返回的Observable发送的事件 = Error事件，则原始的Observable不重新发送事件
                                // 该异常错误信息可在观察者中的onError（）中获得
                                return Observable.error(new Throwable("retryWhen终止啦"));

                                // 2. 若返回的Observable发送的事件 = Next事件，则原始的Observable重新发送事件（若持续遇到错误，则持续重试）
//                                return Observable.just(88);
                            }
                        });
                    }
                })
                .subscribe(new TestObserver<>("retryWhen"));

        // 14. repeat 让被观察者重复发送事件 可以设置次数
        Observable.just(1, 2)
                .repeat(2)
                .subscribe(new TestObserver<>("repeat"));

        // 15. repeatWhen 有条件的让被观察者重复发送事件
        Observable.just(3, 4)
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                        // 下面这三个的现象跟retryWhen是一样的
//                        return Observable.empty();
//                        return Observable.error(new Exception("repeatWhen 发送了error事件"));
                        return Observable.just(1, 2);

                        // TODO: 2022/1/13 这个跟retryWhen现象一致 有时间再看看原因 应该跟flatMap有关系
//                        return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
//                            @Override
//                            public ObservableSource<?> apply(@NonNull Object o) throws Exception {
//                                // 等于发送了onComplete事件 被观察者发送事件 但下游不会收到onComplete事件
//                                return Observable.empty();
//                                // 等于发送了onError事件 被观察者发送事件 并且下游会收到onError事件
////                                return Observable.error(new Exception("repeatWhen 发送了error事件"));
//                                // 被观察者会一直repeat
////                                return Observable.just(1);
//                            }
//                        });
                    }
                })
                .subscribe(new TestObserver<>("repeatWhen"));
    }
}
