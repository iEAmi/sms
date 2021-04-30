package me.ahmad.sms.app.queue

import java.io.Closeable

class QueueStarter internal constructor(
    private val queue: Queue,
    private val consumer: QueueConsumer
) : Closeable by queue {

    fun start() = queue.setConsumer(consumer)
}