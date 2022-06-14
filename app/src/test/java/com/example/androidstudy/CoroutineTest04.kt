package com.example.androidstudy

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import org.junit.Test

/**
 * @author zjy
 * @date 2022/6/14 6:38 下午
 * @description
 */
class CoroutineTest04 {
    @Test
    fun `test deal with timeout`(): Unit = runBlocking {
        withTimeout(1300) {
            repeat(1000) {
                println("job: I'm sleeping $it ...")
                delay(500)
            }
        }
    }

    @Test
    fun `test deal with timeout return null`(): Unit = runBlocking {
        val result = withTimeoutOrNull(1300) {
            repeat(1000) {
                println("job: I'm sleeping $it ...")
                delay(500)
            }
            "Done"
        } ?: "Jack"
        println("Result is $result")
    }
}