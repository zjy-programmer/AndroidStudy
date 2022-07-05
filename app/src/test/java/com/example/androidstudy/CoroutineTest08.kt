package com.example.androidstudy

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import org.junit.Test

/**
 * @author zjy
 * @date 2022/7/4 7:10 下午
 * @description 热流 channel开始学习
 */
class CoroutineTest08 {
    @Test
    fun `test know channel`() = runBlocking {
        val channel = Channel<Int>()
        // 生产者
        val producer = GlobalScope.launch {
            var i = 0
            while (true) {
                delay(1000)
                channel.send(++i)
                println("send $i")
            }
        }
        // 消费者
        val consumer = GlobalScope.launch {
            while (true) {
                val element = channel.receive()
                println("receive $element")
            }
        }
        joinAll(producer, consumer)
    }

    @Test
    fun `test iterate channel`() = runBlocking {
        val channel = Channel<Int>(Channel.UNLIMITED)

        // 生产者
        val producer = GlobalScope.launch {
            for (x in 1..5) {
                channel.send(x * x)
                println("send ${x * x}")
            }
        }

        // 消费者
        val consumer = GlobalScope.launch {
//            val iterator = channel.iterator()
//            while (iterator.hasNext()) {
//                val element = iterator.next()
//                println("receive $element")
//                delay(2000)
//            }
            // 第二种遍历方法
            for (element in channel) {
                println("receive $element")
                delay(2000)
            }
        }
        joinAll(producer, consumer)
    }

    @Test
    fun `test fast producer channel`() = runBlocking {
        val receiveChannel: ReceiveChannel<Int> = GlobalScope.produce {
            repeat(100) {
                delay(1000)
                send(it)
            }
        }

        val consumer = GlobalScope.launch {
            for (i in receiveChannel) {
                println("received: $i")
            }
        }

        joinAll(consumer)
    }

    @Test
    fun `test fast consumer channel`() = runBlocking {
        val sendChannel: SendChannel<Int> = GlobalScope.actor {
            while (true) {
                val element = receive()
                println(element)
            }
        }
        val producer = GlobalScope.launch {
            for (i in 0..3) {
                sendChannel.send(i)
            }
        }
        producer.join()
    }

    @Test
    fun `test close channel`() = runBlocking {
        val channel: Channel<Int> = Channel(3)
        // 生产者
        val producer = GlobalScope.launch {
            List(3) {
                channel.send(it)
                println("send $it")
            }
            channel.close()
            println("""close channel.
                |   - ClosedForSend: ${channel.isClosedForSend}
                |   - ClosedForReceive: ${channel.isClosedForReceive}
            """.trimMargin())
        }

        // 消费者
        val consumer = GlobalScope.launch {
            for (element in channel) {
                println("receive $element")
                delay(1000)
            }
            println("""After Consuming.
                |   - ClosedForSend: ${channel.isClosedForSend}
                |   - ClosedForReceive: ${channel.isClosedForReceive}
            """.trimMargin())
        }
        joinAll(producer, consumer)
    }

    @Test
    fun `test broadcast`() = runBlocking {
//        val broadcastChannel = BroadcastChannel<Int>(Channel.BUFFERED)
        // 也可以通过channel转成BroadcastChannel
        val channel = Channel<Int>()
        val broadcastChannel = channel.broadcast(3)
        val producer = GlobalScope.launch {
            List(3) {
                delay(100)
                broadcastChannel.send(it)
            }
            broadcastChannel.close()
        }

        List(3) {
            GlobalScope.launch {
                val receiveChannel = broadcastChannel.openSubscription()
                for (i in receiveChannel) {
                    println("[#$it] received: $i")
                }
            }
        }.joinAll()
    }
}