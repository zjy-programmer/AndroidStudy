package com.example.androidstudy
import kotlinx.coroutines.*
import org.junit.Test

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.coroutines
 * ClassName: CoroutineTest
 * CreateDate: 2022/5/27 5:12 下午
 * Author: zjy
 * Description:
 */
class CoroutineTest10 {
    @Test
    fun `11`() = runBlocking {
        val job1 = launch {
            delay(200)
            println("job1 finished")
        }

        val job2 = async {
            delay(200)
            println("job2 finished")
            "job2 result"
        }
        println(job2.await())
    }
}