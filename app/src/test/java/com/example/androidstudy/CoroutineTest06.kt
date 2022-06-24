package com.example.androidstudy

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Test

/**
 * @author zjy
 * @date 2022/6/20 6:26 下午
 * @description Flow学习 开始！！！
 */
class CoroutineTest06 {

    // 返回了多个值，但不是异步
    fun simpleList(): List<Int> = listOf(1, 2, 3)
    // 返回了多个值，是同步
    fun simpleSequence(): Sequence<Int> = sequence {
        for (i in 1..3) {
//            Thread.sleep(1000)
//            delay(100) // 受注解限制不允许调用
            yield(i)
        }
    }

    // 返回了多个值 是异步 一次性返回了多个值
    suspend fun simpleList2(): List<Int> {
        delay(1000)
        return listOf(1, 2, 3)
    }

    // 返回多个值 是异步的
    fun simpleFlow() = flow<Int> {
        for (i in 1..3) {
            delay(1000)
            emit(i)
        }
    }

    @Test
    fun `test multiple values`() {
        simpleList().forEach {
            println(it)
        }
        simpleSequence().forEach {
            println(it)
        }
    }

    @Test
    fun `test multiple values2`() = runBlocking {
        simpleList2().forEach {
            println(it)
        }
    }

    @Test
    fun `test multiple values3`() = runBlocking {
        launch {
            for(i in 1..3) {
                println("I'm not blocked $i")
                delay(1500)
            }
        }
        simpleFlow().collect {
            println(it)
        }
    }

    fun simpleFlow2() = flow<Int> {
        println("Flow started")
        for (i in 1..3) {
            delay(1000)
            emit(i)
        }
    }

    // Flow是一种类似序列的冷流，flow构建器中的代码直到流被收集的时候才运行
    @Test
    fun `test flow is cold`() = runBlocking {
        val flow = simpleFlow2()
        println("Calling collect...")
        flow.collect {
            println(it)
        }
        println("Calling collect...")
        flow.collect {
            println(it)
        }
    }

    @Test
    fun `test flow continuation`() = runBlocking {
        (1..5).asFlow()
            .filter { it % 2 == 0 }
            .map { "string $it" }
            .collect { println(it) }
    }

    @Test
    fun `test flow builder`() = runBlocking {
        flowOf("one", "two", "three")
            .onEach { delay(1000) }
            .collect { println(it) }
        (1..3).asFlow().collect { println(it) }
    }

    fun simpleFlow3() = flow<Int> {
        println("Flow started ${Thread.currentThread().name}")
        for (i in 1..3) {
            delay(1000)
            emit(i)
        }
    }

    fun simpleFlow4() = flow<Int> {
        withContext(Dispatchers.IO) {
            println("Flow started ${Thread.currentThread().name}")
            for (i in 1..3) {
                delay(1000)
                emit(i)
            }
        }
    }

    @Test
    fun `test flow context`() = runBlocking {
        simpleFlow4().collect { println("Collected $it ${Thread.currentThread().name}") }
    }

    fun simpleFlow5() = flow<Int> {
        println("Flow started ${Thread.currentThread().name}")
        for (i in 1..3) {
            delay(1000)
            emit(i)
        }
    }.flowOn(Dispatchers.Default)

    @Test
    fun `test flow on`() = runBlocking {
        simpleFlow5().collect { println("Collected $it ${Thread.currentThread().name}") }
    }

    // 事件源
    fun events() = (1..3)
        .asFlow()
        .onEach { delay(100) }
        .flowOn(Dispatchers.Default)

    @Test
    fun `test flow launch`() = runBlocking {
        events().onEach { println("Event: $it ${Thread.currentThread().name}") }
            .launchIn(/*CoroutineScope(Dispatchers.IO)*/this)
            .join()
    }
}