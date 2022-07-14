package com.example.androidstudy

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy
 * ClassName: CoroutineTest07
 * CreateDate: 2022/6/25 12:00 下午
 * Author: zjy
 * Description:
 */
class CoroutineTest07 {
    suspend fun performRequest(request: Int): String {
        delay(1000)
        return "response $request"
    }

    @Test
    fun `test transform flow operator`() = runBlocking {
        // map 传一个进来发一个出去
//        (1..3).asFlow()
//            .map { performRequest(it) }
//            .collect { println(it) }

        // transform 传一个进来可以发射多个出去
        (1..3).asFlow()
            .transform {
                emit("Making request $it")
                emit(performRequest(it))
            }
            .collect { println(it) }
    }

    fun numbers() = flow<Int> {
        try {
            emit(1)
            emit(2)
            println("This line will not execute")
            emit(3)
        } finally {
            println("Finally in numbers")
        }
    }

    // 只取两个
    @Test
    fun `test limit length operator`() = runBlocking {
        numbers().take(2).collect { println(it) }
    }

    @Test
    fun `test terminal operator`() = runBlocking {
        val sum = (1..5).asFlow()
            .map { it * it }
            .reduce { a, b -> a + b }
        println(sum)
    }

    @Test
    fun `test zip`() = runBlocking {
        val nums = (1..3).asFlow()
        val strs = flowOf("One", "Two", "Three")
        nums.zip(strs) { a, b ->
            "$a -> $b"
        }.collect { println(it) }
    }

    @Test
    fun `test zip2`() = runBlocking {
        val nums = (1..3).asFlow().onEach { delay(300) }
        val strs = flowOf("One", "Two", "Three").onEach { delay(400) }
        val startTime = System.currentTimeMillis()
        nums.zip(strs) { a, b ->
            "$a -> $b"
        }.collect { println("$it at ${System.currentTimeMillis() - startTime} ms from start") }
    }

    fun requestFlow(i: Int) = flow<String> {
        emit("$i: First")
        delay(500)
        emit("$i: Second")
    }

    @Test
    fun `test flatMapConcat`() = runBlocking {
        val startTime = System.currentTimeMillis()
        (1..3).asFlow()
            .onEach { delay(100) }
//            .map { requestFlow(it) } //Flow<Flow<String>>
            .flatMapConcat { requestFlow(it) }
            .collect { println("$it at ${System.currentTimeMillis() - startTime} ms from start") }
    }

    @Test
    fun `test flatMapMerge`() = runBlocking {
        val startTime = System.currentTimeMillis()
        (1..3).asFlow()
            .onEach { delay(100) }
            .flatMapMerge { requestFlow(it) }
            .collect { println("$it at ${System.currentTimeMillis() - startTime} ms from start") }
    }

    @Test
    fun `test flatMapLatest`() = runBlocking {
        val startTime = System.currentTimeMillis()
        (1..3).asFlow()
            .onEach { delay(100) }
            .flatMapLatest { requestFlow(it) }
            .collect { println("$it at ${System.currentTimeMillis() - startTime} ms from start") }
    }

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