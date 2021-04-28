package me.ahmad.sms.app.queue

import io.reactivex.rxjava3.core.Flowable
import java.io.Closeable

abstract class Queue : Flowable<QueueContext>(), Closeable