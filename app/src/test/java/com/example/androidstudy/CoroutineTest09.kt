package com.example.androidstudy

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.selects.select
import org.junit.Test

/**
 * @author zjy
 * @date 2022/7/5 2:51 下午
 * @description
 */

fun CoroutineScope.getUserFromLocal(name: String) = async(Dispatchers.IO) {
    delay(2000)
    "$name 本地"
}

fun CoroutineScope.getUserFromRemote(name: String) = async(Dispatchers.IO) {
    delay(1000)
    "$name 远程"
}

class CoroutineTest09 {
    @Test
    fun `test select await`() = runBlocking {
        val localRequest = getUserFromLocal("jack")
        val remoteRequest = getUserFromRemote("tom")

        val userResponse = select<String> {
            localRequest.onAwait { it }
            remoteRequest.onAwait { it }
        }

        println(userResponse)
    }

    @Test
    fun `test select channel`() = runBlocking {
        val channels = listOf<Channel<Int>>(Channel(), Channel())
        GlobalScope.launch {
            delay(100)
            channels[0].send(200)
        }
        GlobalScope.launch {
            delay(50)
            channels[1].send(100)
        }
        val result = select<Int> {
            channels.forEach { channel ->
                channel.onReceive { it }
            }
        }
        println(result)
    }

    @Test
    fun `test SelectClause0`() = runBlocking {
        val job1 = GlobalScope.launch {
            delay(100)
            println("job 1")
        }

        val job2 = GlobalScope.launch {
            delay(10)
            println("job 2")
        }

        select<Unit> {
            job1.onJoin { println("job 1 onJoin")}
            job2.onJoin { println("job 2 onJoin")}
        }
    }

    @Test
    fun `test SelectClause2`() = runBlocking {
        val channels = listOf<Channel<Int>>(Channel(), Channel())
        println(channels)

        launch(Dispatchers.IO) {
            select<Unit?> {
                launch {
                    delay(100)
                    channels[0].onSend(200) {
                        println("send on $it")
                    }
                }

                launch {
                    delay(10)
                    channels[1].onSend(300) {
                        println("send on $it")
                    }
                }
            }
        }

        GlobalScope.launch {
            println(channels[0].receive())
        }
        GlobalScope.launch {
            println(channels[1].receive())
        }
        delay(1000)
    }

    @Test
    fun `test select flow`() = runBlocking {
        // 函数 -> 协程 -> Flow -> Flow合并
        val name = "guest"
        coroutineScope {
            listOf(::getUserFromLocal, ::getUserFromRemote)
                .map { function ->
                    function.call(name)
                }.map {  deferred ->
                    flow { emit(deferred.await()) }
                }.merge()
                .collect { user ->
                    println(user)
                }
        }
    }
}