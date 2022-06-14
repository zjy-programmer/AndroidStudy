package com.example.androidstudy

import kotlinx.coroutines.*
import org.junit.Test
import java.io.BufferedReader
import java.io.FileReader

/**
 * @author zjy
 * @date 2022/6/14 6:16 下午
 * @description
 */
class CoroutineTest03 {
    @Test
    fun `test release resources`(): Unit = runBlocking {
        val job = launch {
            try {
                repeat(1000) {
                    println("job: I'm sleeping $it ...")
                    delay(500)
                }
            } finally {
                println("job: I'm running finally")
            }
        }
        delay(1300)
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }

    @Test
    fun `test use function`(): Unit = runBlocking {
        BufferedReader(FileReader("/Users/pxcm-0101-01-0094/Desktop/1.txt")).use {
            var line: String?
            while (true) {
                line = it.readLine() ?: break
                println(line)
            }
        }
    }

    @Test
    fun `test cancel with NonCancellable`(): Unit = runBlocking {
        val job = launch {
            try {
                repeat(1000) {
                    println("job: I'm sleeping $it ...")
                    delay(500)
                }
            } finally {
                withContext(NonCancellable) {
                    println("job: I'm running finally")
                    delay(1000)
                    println("job: And I've just delayed for 1 sec because I'm non-cancellable")
                }
            }
        }
        delay(1300)
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }
}