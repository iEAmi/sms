package me.ahmad.sms.domain.event

import me.ahmad.sms.domain.Entity
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

interface Event<T : Entity<*>> {
    val id: UUID
    val entity: T
    val creationDate: LocalDateTime
}

abstract class AbstractEvent<T : Entity<*>>(
    override val id: UUID = UUID.randomUUID(),
    override val creationDate: LocalDateTime = LocalDateTime.now(ZoneOffset.UTC)
) : Event<T>