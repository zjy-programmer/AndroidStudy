package com.example.androidstudy.any.rxjava.observer;

import com.example.baselibrary.util.LogUtil;

import androidx.annotation.NonNull;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.rxjava.observer
 * ClassName: TestObserver
 * CreateDate: 2022/1/13 7:58 下午
 * Author: zjy
 * Description: 用于学习Rxjava写的Observer 方便打印观察者的数据
 */
public class TestObserver<T> implements Observer<T> {
    public static final String TAG = "zjy";
    private String operatorName;
    public Disposable disposable;

    public TestObserver(String operatorName) {
        this.operatorName = operatorName;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        disposable = d;
        LogUtil.i(TAG, operatorName + "操作符 下游 onSubscribe");
    }

    @Override
    public void onNext(@NonNull T t) {
        LogUtil.i(TAG, operatorName + "操作符 下游 onNext：" + t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        LogUtil.i(TAG, operatorName + "操作符 下游 onError：" + e.getMessage());

    }

    @Override
    public void onComplete() {
        LogUtil.i(TAG, operatorName + "操作符 下游 onComplete");
    }
    
}
