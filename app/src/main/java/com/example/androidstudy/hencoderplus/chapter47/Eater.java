package com.example.androidstudy.hencoderplus.chapter47;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.hencoderplus.chapter47
 * ClassName: Eater
 * CreateDate: 2021/10/28 5:02 下午
 * Author: zjy
 * Description: eat()的参数food的类型使用泛型来表示
 */

//interface Eater {
//    void eat(Object food);
//}

interface Eater<Food> {
    void eat(Food food);
}
