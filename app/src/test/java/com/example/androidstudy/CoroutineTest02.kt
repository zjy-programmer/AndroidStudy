package com.example.androidstudy

import kotlinx.coroutines.*
import org.junit.Test
import java.lang.IllegalArgumentException

/**
 * @author zjy
 * @date 2022/6/14 3:55 下午
 * @description
 */
class CoroutineTest02 {
    @Test
    fun `test cancel`(): Unit = runBlocking {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            delay(1000)
            println("job 1")
        }

        scope.launch {
            delay(1000)
            println("job 2")
        }
    }

    @Test
    fun `test brother cancel`(): Unit = runBlocking {
        val scope = CoroutineScope(Dispatchers.Default)
        val job1 = scope.launch {
            delay(1000)
            println("job 1")
        }

        val job2 = scope.launch {
            delay(1000)
            println("job 2")
        }

        delay(100)
        job1.cancel()
        delay(2000)
    }

    @Test
    fun `test CancellationException`(): Unit = runBlocking {
        val job1 = GlobalScope.launch {
            try {
                delay(1000)
                println("job 1")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        delay(100)
//        job1.cancel(CancellationException("取消"))
//        job1.join()
        job1.cancelAndJoin()
    }

    @Test
    fun `test cancel cpu task by isActive`(): Unit = runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5 && isActive) {
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500
                }
            }
        }

        delay(1300)
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }

    @Test
    fun `test cancel cpu task by ensureActive`(): Unit = runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5) {
                // 可以抛个异常 注意会被静默处理 需要主动catch
                ensureActive()
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500
                }
            }
        }

        delay(1300)
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }

    @Test
    fun `test cancel cpu task by yield`(): Unit = runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5) {
                // 这个用法感觉很奇怪 还是用 isActive 或 ensureActive 感觉好一些
                yield()
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500
                }
            }
        }

        delay(1300)
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }
}