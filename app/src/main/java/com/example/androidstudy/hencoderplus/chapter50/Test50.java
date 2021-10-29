package com.example.androidstudy.hencoderplus.chapter50;

import java.util.List;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.hencoderplus.chapter50
 * ClassName: Test50
 * CreateDate: 2021/10/29 11:43 上午
 * Author: zjy
 * Description:
 */
public class Test50 {
    /**
     * 1. 把参数param的类型限制为必须同时满足：
     *    1. 是Runnable
     *    2. 是Serializable
     */
    void someMethod(Object param) {
        // 忘了怎么多限制了
    }

    /**
     * 题目在第二页 不过跟我猜的一样
     * 2. 放宽参数限制，改为填任何类型的参数都行，只要参数item的类型和参数list的List<>尖括号里的类型一致就可以
     */
//    public void merge(Object item, List<Object> list) {
//        list.add(item);
//    }

    <E> void merge(E item, List<E> list) {
        list.add(item);
    }
}
