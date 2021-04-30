package me.ahmad.sms.app.queue

interface QueueConsumer {

    fun onEvent(event: QueueContext)
}