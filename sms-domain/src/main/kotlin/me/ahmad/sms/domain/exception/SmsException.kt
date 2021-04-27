package me.ahmad.sms.domain.exception

abstract class SmsException
protected constructor(
    override val message: String
) : RuntimeException(message) {

    open class InvalidArgument
    internal constructor(
        override val message: String
    ) : SmsException(message)

    class NullArgument
    internal constructor(
        override val message: String
    ) : InvalidArgument(message)

    class WrappedException
    internal constructor(
        override val message: String
    ) : SmsException(message)

    companion object {
        fun invalidArg(message: String): InvalidArgument = InvalidArgument(message)
        fun nullArg(message: String): NullArgument = NullArgument(message)
    }
}