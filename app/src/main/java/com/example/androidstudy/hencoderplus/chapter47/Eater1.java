package com.example.androidstudy.hencoderplus.chapter47;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.hencoderplus.chapter47
 * ClassName: Eater1
 * CreateDate: 2021/10/28 6:51 下午
 * Author: zjy
 * Description: 让eat()方法的参数food依然保留为泛型形式，但把类型限制为只能是Food类型的
 */

//interface Eater1<T> {
//    void eat(T food);
//}

interface Eater1<T> {
    <F extends Food>void eat(F food);
}