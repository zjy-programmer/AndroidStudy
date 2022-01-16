package com.example.androidstudy.any.rxjava.api;

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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.rxjava.api
 * ClassName: RxjavaTestImpl2
 * CreateDate: 2022/1/13 11:22 上午
 * Author: zjy
 * Description: 创建型操作符
 */
public class RxjavaTestImpl2 implements IRxjavaTest {
    private Integer deferI = 1;

    @Override
    public void run() {
        // 1. create
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogUtil.i(TAG, "create操作符：" + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        // 2. just
        Observable.just(1, 2).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogUtil.i(TAG, "just操作符：" + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        // 3. fromArray
        Observable.fromArray(new int[]{1, 2, 3}).subscribe(new Observer<int[]>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull int[] ints) {
                for (int i : ints) {
                    LogUtil.i(TAG, "fromArray操作符：" + i);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        // 4. fromIterable
        ArrayList<Integer> list = new ArrayList<Integer>() {
            {
                add(1);
                add(2);
                add(3);
            }
        };
        Observable.fromIterable(list).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogUtil.i(TAG, "fromIterable操作符：" + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        // 5. empty
        Observable.empty().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Object o) {
                LogUtil.i(TAG, "empty操作符：" + o);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                LogUtil.i(TAG, "empty操作符只发送complete事件 用于测试 下游只能用object接 因为源码中只接受 T super Object 类型");
            }
        });

        // 6. error
        Observable.error(new RuntimeException("通过error操作符发送")).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Object o) {

            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogUtil.i(TAG, "error操作符只发送error事件 用于测试 下游只能用object接 因为源码中没有实例化泛型" + "\n" + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

        // 7. never 只会调用onSubscribe 其他的方法都不会回调
        Observable.never().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                LogUtil.i(TAG, "never操作符 onSubscribe");
            }

            @Override
            public void onNext(@NonNull Object o) {
                LogUtil.i(TAG, "never操作符 onNext");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogUtil.i(TAG, "never操作符 onError");
            }

            @Override
            public void onComplete() {
                LogUtil.i(TAG, "never操作符 onComplete");
            }
        });

        // 8. defer 相当于懒加载 获得的肯定是最新值 可以用create自己实现相同逻辑 但是类似just操作符就不行获取的是旧的数
        Observable<Integer> defer = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(deferI);
            }
        });
        deferI = 2;
        defer.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogUtil.i(TAG, "defer操作符 onNext：" + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        // 9. timer
        Observable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        LogUtil.i(TAG, "timer操作符 延迟2秒发送一个Long类型数 值为：" + aLong + " 默认是在computation线程");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // 10. interval
        Observable.interval(3, 1, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    Disposable disposable;
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        if (aLong == 3) {
                            disposable.dispose();
                        }
                        LogUtil.i(TAG, "interval操作符 从0开始发送 延迟3秒开始 每一秒产生一个数字 当前数字：" + aLong);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // 11. intervalRange
        Observable.intervalRange(3, 10, 2, 1, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        LogUtil.i(TAG, "intervalRange操作符 从start即3开始发送 延迟2秒开始 每一秒产生一个数字 总共产生10个数字 当前数字：" + aLong);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // 12. range
        Observable.range(3, 10).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogUtil.i(TAG, "range操作符 从start即3开始发送 总共产生10个数字 当前数字：" + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        // 13. rangeLong 和 range的区别在于泛型是Long类型
        Observable.rangeLong(3, 5).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Long aLong) {
                LogUtil.i(TAG, "rangeLong操作符 从start即3开始发送 总共产生10个数字 当前数字：" + aLong);
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
