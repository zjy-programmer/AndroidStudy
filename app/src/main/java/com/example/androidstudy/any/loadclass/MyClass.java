package com.example.androidstudy.any.loadclass;

import com.example.baselibrary.util.LogUtil;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.loadclass
 * ClassName: MyClass
 * CreateDate: 2022/2/14 4:54 下午
 * Author: zjy
 * Description:
 */
public class MyClass {
    public static int value = 1;

    // 类在jvm的加载过程分 装载 链接 初始化 其中链接细分为验证 准备 解析
    // 静态代码块和静态变量会在准备阶段赋"0"值 初始化阶段真正赋值
    static {
        LogUtil.i("zjy", "静态代码块输出的");
    }

    // 初始化对象时会初始化非静态代码块和实例变量
    {
        LogUtil.i("zjy", "非静态代码块输出的");
    }
}
