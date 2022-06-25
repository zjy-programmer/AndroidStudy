package com.example.androidstudy

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Test
import kotlin.system.measureTimeMillis

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
            for (i in 1..3) {
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

    fun simpleFlow6() = flow<Int> {
        for (i in 1..3) {
            delay(1000)
            emit(i)
            println("Emitting $i")
        }
    }

    @Test
    fun `test cancel flow`() = runBlocking {
        withTimeoutOrNull(2500) {
            simpleFlow6().collect { println(it) }
        }
        println("Done")
    }

    fun simpleFlow7() = flow<Int> {
        for (i in 1..5) {
            emit(i)
            println("Emitting $i")
        }
    }

    @Test
    fun `test cancel flow check`() = runBlocking {
//        simpleFlow7().collect {
//            println(it)
//            if (it == 3) {
//                cancel()
//            }
//        }
        // 这样取消是不行的 来不及取消了
//        (1..5).asFlow().collect {
//            println(it)
//            if (it == 3) {
//                cancel()
//            }
//        }

        // 需要加上cancellable才能取消
        (1..5).asFlow().cancellable().collect {
            println(it)
            if (it == 3) {
                cancel()
            }
        }
    }

    fun simpleFlow8() = flow<Int> {
        for (i in 1..3) {
            delay(100)
            emit(i)
            println("Emitting $i ${Thread.currentThread().name} 发射事件的时间：${System.currentTimeMillis()}")
        }
    }

    @Test
    fun `test flow back pressure`() = runBlocking {
//        val time  = measureTimeMillis {
//            simpleFlow8().collect {
//                delay(300)
//                println("Collected $it ${Thread.currentThread().name}")
//            }
//        }
//        println("Collected in $time ms")

        // 优化一下 加缓存 每100ms往buffer发事件 collect是从buffer里取事件
//        val time = measureTimeMillis {
//            println("调用simpleFlow8前：${System.currentTimeMillis()}")
//            simpleFlow8()
//                .buffer(50)
//                .collect {
//                    delay(300)
//                    println("Collected $it ${Thread.currentThread().name}")
//                }
//        }
//        println("Collected in $time ms")

        // 上边发送和收集在同一个线程 切换个线程也可以做到
//        val time = measureTimeMillis {
//            println("调用simpleFlow8前：${System.currentTimeMillis()}")
//            simpleFlow8()
//                .flowOn(Dispatchers.Default)
//                .collect {
//                    delay(300)
//                    println("Collected $it ${Thread.currentThread().name}")
//                }
//        }
//        println("Collected in $time ms")

        // 上边几种方式是通过改变上游的发送速度 下面是改变下游的处理速度
        // 或者使用conflate 这个会丢中间的数据
//        val time = measureTimeMillis {
//            println("调用simpleFlow8前：${System.currentTimeMillis()}")
//            simpleFlow8()
//                .conflate()
//                .collect {
//                    delay(300)
//                    println("Collected $it ${Thread.currentThread().name}")
//                }
//        }
//        println("Collected in $time ms")

        // 或使用collectLatest 只处理最后一个
        val time = measureTimeMillis {
            println("调用simpleFlow8前：${System.currentTimeMillis()}")
            simpleFlow8()
                .collectLatest {
                    delay(300)
                    println("Collected $it ${Thread.currentThread().name}")
                }
        }
        println("Collected in $time ms")
    }
}