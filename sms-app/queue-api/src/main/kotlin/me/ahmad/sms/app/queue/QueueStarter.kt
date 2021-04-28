package me.ahmad.sms.app.queue

import java.io.Closeable

class QueueStarter internal constructor(
    private val queue: Queue,
    private val consumer: SmsQueueConsumer
) : Closeable by queue {

    fun start() = queue.subscribe(consumer)
}