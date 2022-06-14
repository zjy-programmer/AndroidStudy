package com.example.androidstudy

import kotlinx.coroutines.*
import org.junit.Test
import java.lang.IllegalArgumentException
import kotlin.system.measureTimeMillis

/**
 * @author zjy
 * @date 2022/6/14 2:50 下午
 * @description
 */
class CoroutineTest {
    @Test
    fun `test sync`() = runBlocking {
        val time = measureTimeMillis {
            val one = doOne()
            val two = doTwo()
            println("The result:${one + two}")
        }
        println("Completed in $time ms")
    }

    @Test
    fun `test combine async`() = runBlocking {
        val time = measureTimeMillis {
            val one = async { doOne() }
            val two = async { doTwo() }
            println("The result:${one.await() + two.await()}")
        }
        println("Completed in $time ms")
    }

    private suspend fun doOne(): Int {
        delay(1000)
        return 14
    }

    private suspend fun doTwo(): Int {
        delay(1000)
        return 25
    }

    @Test
    fun `test start mode`() = runBlocking {
        val job = launch(start = CoroutineStart.DEFAULT) {
            delay(10_000)
            println("Job finished.")
        }
        delay(1000)
        job.cancel()
    }

    @Test
    fun `test coroutine scope builder`() = runBlocking {
        // 一个协程失败了 所以其他兄弟协程也会被取消
        coroutineScope {
            val job1 = launch {
                delay(400)
                println("job1 finished.")
            }
            val job2 = async {
                delay(200)
                println("job2 finished.")
                throw IllegalArgumentException()
            }
        }
    }

    @Test
    fun `test supervisor scope builder`() = runBlocking {
        // 一个协程失败了 不会影响其他兄弟协程
        supervisorScope {
            val job1 = launch {
                delay(400)
                println("job1 finished.")
            }
            val job2 = async {
                delay(200)
                println("job2 finished.")
                throw IllegalArgumentException()
            }
        }
    }
}