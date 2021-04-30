package me.ahmad.sms.app.queue

import java.io.Closeable

interface  Queue : Closeable {

    fun setConsumer(consumer: QueueConsumer)
}