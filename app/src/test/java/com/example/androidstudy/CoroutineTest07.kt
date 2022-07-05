package com.example.androidstudy

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * @author zjy
 * @date 2022/7/4 6:31 下午
 * @description
 */
class CoroutineTest07 {
    fun simpleFlow() = flow<Int> {
        for (i in 1..3) {
            println("Emitting $i")
            emit(i)
        }
    }

    @Test
    fun `test flow exception`() = runBlocking {
        try {
            simpleFlow().collect {
                println(it)
                check(it <= 1) {
                    "Collected $it"
                }
            }
        } catch (e: Exception) {
            println("Caught $e")
        }
    }

    // try/catch块一般用来捕获下游异常
    @Test
    fun `test flow exception2`() = runBlocking {
//        flow {
//            emit(1)
//            throw ArithmeticException("Div 0")
//        }.catch { e: Throwable -> println("Caught $e") } // 这的catch 是来捕获上游的异常的
//            .flowOn(Dispatchers.IO)
//            .collect { println(it) }

        flow {
            emit(1)
            throw ArithmeticException("Div 0")
        }.catch { e: Throwable ->
            println("Caught $e")
            emit(10) // 异常恢复 这里的10依然可以发出去并被下游接收到
        } // 这的catch 是来捕获上游的异常的
            .flowOn(Dispatchers.IO)
            .collect { println(it) }
    }

    fun simpleFlow2() = (1..3).asFlow()

    @Test
    fun `test flow complete in finally`() = runBlocking {
        try {
            simpleFlow2().collect { println(it) }
        } finally {
            println("Done")
        }
    }

    fun simpleFlow3() = flow<Int> {
        emit(1)
        throw RuntimeException()
    }

    @Test
    fun `test flow complete in onCompletion`() = runBlocking {
//        simpleFlow2()
//            .onCompletion { println("Done") }
//            .collect { println(it) }

//        simpleFlow3()
//            .onCompletion { exception ->
//                if (exception != null) {
//                    // 可以获取到异常 但是不会捕获 如果想捕获还需catch
//                    println("Flow completed exception $exception")
//                }
//            }
//            .collect { println(it) }

//        simpleFlow3()
//            .onCompletion { exception ->
//                if (exception != null) {
//                    // 可以获取到异常 但是不会捕获 如果想捕获还需catch
//                    println("Flow completed exception $exception")
//                }
//            }
//            .catch { println(it) }
//            .collect { println(it) }

        simpleFlow3()
            .onCompletion { exception ->
                if (exception != null) {
                    // 下游异常也可以获取到 但是想捕获就需要try/catch了
                    println("Flow completed exception $exception")
                }
            }
            .collect {
                println(it)
                check(it <= 0) {
                    throw IllegalArgumentException("下游异常")
                }
            }
    }

}