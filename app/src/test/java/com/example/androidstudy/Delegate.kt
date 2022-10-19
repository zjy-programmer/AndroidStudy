package com.example.androidstudy

import org.junit.Test
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author zjy
 * @date 2022/9/13 2:21 下午
 * @description
 */
class Delegate {
    class Person {
        var name by ReadWriteDelegate("mihawk")
    }

    class ReadWriteDelegate(var name: String) : ReadWriteProperty<Person, String> {
        override fun getValue(thisRef: Person, property: KProperty<*>): String {
            return this.name
        }

        override fun setValue(thisRef: Person, property: KProperty<*>, value: String) {
            this.name = value.substring(0, 1).uppercase() + value.substring(1)
        }

    }

    @Test
    fun `自定义var修饰的变量的属性委托`() {
//        val person = Person()
//        person.name = "mihawk"
        println(Person().name)
    }

    lateinit var type1: Any
    fun whenTest() {
        when(type1) {
            "1" -> {
                println("111")
            }
            "2" -> {
                println("222")
            }
            is String -> {
                println("是 String")
            }
            is Int -> {
                println("是 Int")
            }
        }
    }

    fun whenReturn() : String = when("3") {
        "1" -> {
            "1"
        }
        "2" -> {
            "2"
        }
        else -> {
            "3"
        }
    }

    val higherFun: (Int) -> String = {
        "返回 $it str"
    }

    fun higherFunParams(p1: String, f: (Int) -> String) {
        println("${p1}1 高阶函数做参数 ${f(111)}")
    }

    fun higherFunReturn(p: String): (String, String) -> String {
        return {p1, p2-> "$p $p1 $p2"}
    }

    @Test
    fun higherFunTest() {
        println(higherFun(111))
        println(higherFun.invoke(222))
        higherFunParams("111", {p -> "我是参数  $p"})
        higherFunParams("111", {"我是参数  $it"})
        higherFunParams("111") { "我是参数  $it" }
        val higherFunReturn = higherFunReturn("666")
        higherFunReturn("", "")
        higherFunReturn.invoke("123", "456")
    }

}