package com.example.androidstudy

import kotlinx.coroutines.*
import org.junit.Test
import kotlin.ArithmeticException

/**
 * @author zjy
 * @date 2022/6/15 10:49 上午
 * @description
 */
class CoroutineTest05 {
    @Test
    fun `test CoroutineContext`(): Unit = runBlocking {
        launch(Dispatchers.Default + CoroutineName("test")) {
            println("I'm working in thread ${Thread.currentThread().name}")
        }
    }

    @Test
    fun `test CoroutineContext extend`(): Unit = runBlocking {
        val scope = CoroutineScope(Job() + Dispatchers.IO + CoroutineName("test"))
        val job = scope.launch {
            println("${coroutineContext[Job]}  ${Thread.currentThread().name}")
            val result = async {
                println("${coroutineContext[Job]}  ${Thread.currentThread().name}")
                "OK"
            }.await()
        }
        job.join()
    }

    @Test
    fun `test CoroutineContext extend2`(): Unit = runBlocking {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            println("Caught $exception")
        }
        val scope = CoroutineScope(Job() + Dispatchers.Main + coroutineExceptionHandler)
        val job = scope.launch(Dispatchers.IO) {

        }
    }

    @Test
    fun `test exception propagation`(): Unit = runBlocking {
        val job = GlobalScope.launch {
            try {
                println("执行 launch")
                throw IndexOutOfBoundsException()
            } catch (e: Exception) {
                println("捕捉 IndexOutOfBoundsException")
            }
        }
        job.join()
        val deferred = GlobalScope.async {
            println("执行 async")
            throw  ArithmeticException()
        }
        try {
            deferred.await()
        } catch (e: Exception) {
            println("捕捉 ArithmeticException")
        }
    }

    @Test
    fun `test exception propagation2`(): Unit = runBlocking {
//        val scope = CoroutineScope(Job())
//        val job = scope.launch {
//            async {
//                throw IllegalArgumentException()
//            }
//        }
//        job.join()

//        val scope = CoroutineScope(Job())
//        val job = scope.async {
//            async {
//                throw IllegalArgumentException()
//            }
//        }
//        job.join()
//        delay(1000)
//        job.await()

        val scope = CoroutineScope(Job())
        val job = scope.async {
            launch {
                throw IllegalArgumentException()
            }
        }
        job.join()
        delay(1000)
//        job.await()
    }

    @Test
    fun `test SupervisorJob`(): Unit = runBlocking {
        val supervisor = CoroutineScope(SupervisorJob())
        val job1 = supervisor.launch {
            delay(100)
            println("child 1")
            throw IllegalArgumentException()
        }

        val job2 = supervisor.launch {
            try {
                delay(Long.MAX_VALUE)
            } finally {
                println("child 2")
            }
        }

        joinAll(job1, job2)
    }

    @Test
    fun `test supervisorScope`(): Unit = runBlocking {
        supervisorScope {
            launch {
                delay(100)
                println("child 1")
                throw IllegalArgumentException()
            }

            try {
                delay(Long.MAX_VALUE)
            } finally {
                println("child 2")
            }
        }
    }

    @Test
    fun `test supervisorScope2`(): Unit = runBlocking {
        supervisorScope {
            val child = launch {
                try {
                    println("The child is sleeping")
                    delay(Long.MAX_VALUE)
                } finally {
                    println("The child is cancelled")
                }
            }
            yield()
            println("Throwing an exception from the scope")
            throw AssertionError()
        }
    }

    @Test
    fun `test CoroutineExceptionHandler`(): Unit = runBlocking {
        val handler = CoroutineExceptionHandler{_, exception ->
            println("Caught $exception")
        }

        val job = GlobalScope.launch(handler) {
            throw IndexOutOfBoundsException()
        }

        val deferred = GlobalScope.async(handler) {
            throw ArithmeticException()
        }

        job.join()
        deferred.await()
    }

    @Test
    fun `test CoroutineExceptionHandler2`(): Unit = runBlocking {
        val handler = CoroutineExceptionHandler{_, exception ->
            println("Caught $exception")
        }
        val scope = CoroutineScope(Job())
        val job = scope.launch(handler) {
            launch {
                throw IllegalArgumentException()
            }
        }
        job.join()
    }

    @Test
    fun `test CoroutineExceptionHandler3`(): Unit = runBlocking {
        val handler = CoroutineExceptionHandler{_, exception ->
            println("Caught $exception")
        }
        val scope = CoroutineScope(Job())
        val job = scope.launch {
            launch(handler) {
                throw IllegalArgumentException()
            }
        }
        job.join()
    }
}