package com.example.androidstudy.any

import java.lang.StringBuilder

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any
 * ClassName: Temp
 * CreateDate: 2022/2/28 9:51 下午
 * Author: zjy
 * Description:
 */
fun main() {
    var s: String = "123".run {
        this + "456"
    }.run {
        println(this)
        this + "789"
    }

    println(s)

    val s1: String? = null
    println(s1.takeUnless { it.isNullOrEmpty() } ?: "字符串是空")

    println(A(-99).age)
    println(A(18).age)

    Logic().apply {
        useType = "use3.0"
        printUsbType()
    }

    Logic1("usb4.0").printUsbType()

    test1(null)
    val sb: StringBuilder = StringBuilder()
    val apply = sb.apply {
        append("1")
        append("2")
        append("3")
    }
    println("sb:$sb")
    println("apply:$apply")

}

class A(_age: Int) {
    val age = _age
        get() = if (field < 0) 0 else field
}

enum class Week(day: String) {
    week1("1"),
    week2("2"),
    week3("3"),
}

interface IUsb {
    var useType: String

    fun printUsbType(): Boolean
}

class Logic : IUsb {
    override var useType: String = ""
        get() = field
        set(value) {field = "【$value】"}

    override fun printUsbType(): Boolean {
        println("usbType:$useType")
        return true;
    }
}

class Logic1(override var useType: String): IUsb {
    override fun printUsbType(): Boolean {
        println("Logic1 打印usbType:$useType")
        return true
    }

}

fun <T> test1(temp: T) {
    println(temp?.toString())
}