package com.example.androidstudy

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.sync.withPermit
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author zjy
 * @date 2022/7/7 2:36 下午
 * @description 线程安全
 */
class CoroutineTest10 {
    @Test
    fun `test not safe concurrent`() = runBlocking {
        var count = 0
        List(1000) {
            GlobalScope.launch { count++ }
        }.joinAll()
        println(count)
    }

    @Test
    fun `test safe concurrent`() = runBlocking {
        var count: AtomicInteger = AtomicInteger(0)
        List(1000) {
            GlobalScope.launch { count.incrementAndGet() }
        }.joinAll()
        println(count.get())
    }

    // 轻量级锁
    @Test
    fun `test safe concurrent tools`() = runBlocking {
        var count = 0
        val mutex = Mutex()
        List(1000) {
            GlobalScope.launch {
                mutex.withLock {
                    count++
                }
            }
        }.joinAll()
        println(count)
    }

    // 信号量
    @Test
    fun `test safe concurrent tools2`() = runBlocking {
        var count = 0
        val semaphore = Semaphore(1)
        List(1000) {
            GlobalScope.launch {
                semaphore.withPermit {
                    count++
                }
            }
        }.joinAll()
        println(count)
    }

     // 编写函数时要求它不得访问外部状态，只能基于参数做运算，通过返回值提供运算结果
     @Test
     fun `test avoid access outer variable`() = runBlocking {
         var count = 0
         val result = count + List(1000) {
             GlobalScope.async { 1 }
         }.map { it.await() }.sum()
         println(result)
     }
}