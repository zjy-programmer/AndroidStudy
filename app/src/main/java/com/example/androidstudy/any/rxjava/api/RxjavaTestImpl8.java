package com.example.androidstudy.any.rxjava.api;

import com.example.baselibrary.util.LogUtil;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.rxjava.api
 * ClassName: RxjavaTestImpl8
 * CreateDate: 2022/1/14 5:40 下午
 * Author: zjy
 * Description: 线程控制
 */
public class RxjavaTestImpl8 implements IRxjavaTest {
    @Override
    public void run() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                LogUtil.i(TAG, "被观察者的线程：" + Thread.currentThread().getName());
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onComplete();
            }
        });

        Observer<Integer> observer = new Observer<Integer>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LogUtil.i(TAG, "观察者的线程：" + Thread.currentThread().getName());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        // 被观察者切换多次 只有第一次有效
//        observable
//                .subscribeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);

        // 观察者切换多次 每次切换都有效
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                // 这个观察者 是在 newThread线程
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtil.i(TAG, "第一次切换 观察者的线程：" + Thread.currentThread().getName());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                // 这个观察者再mainThread线程
                .subscribe(observer);

        // 接口已经不能用了
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetRequest getRequest = retrofit.create(GetRequest.class);
        Observable<Translation> call = getRequest.getCall();
        call
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Translation translation) {
                        translation.show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtil.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    interface GetRequest {
        @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
        Observable<Translation> getCall();
    }

    // 金山词霸返回格式
    private static class Translation {
        private int status;

        private content content;

        private static class content {
            private String from;
            private String to;
            private String vendor;
            private String out;
            private int errNo;
        }

        //定义 输出返回数据 的方法
        public void show() {
            System.out.println("Rxjava翻译结果：" + status);
            System.out.println("Rxjava翻译结果：" + content.from);
            System.out.println("Rxjava翻译结果：" + content.to);
            System.out.println("Rxjava翻译结果：" + content.vendor);
            System.out.println("Rxjava翻译结果：" + content.out);
            System.out.println("Rxjava翻译结果：" + content.errNo);
        }
    }

}
