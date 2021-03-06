package me.ahmad.sms.domain

abstract class SmsException
protected constructor(
    open val type: String,
    open val title: String,
) : RuntimeException("{ \"type\":\"$type\",\"title\":\"$title\" }") {
    var details: String? = null
        private set

    private constructor(
        type: String,
        title: String,
        details: String?,
    ) : this(type, title) {
        this.details = details
    }

    fun withDetails(d: String?): SmsException = object : SmsException(type, title, d) {}

    open class InvalidArgument protected constructor(type: String, title: String) :
        SmsException(type, title) {
        internal constructor(title: String) : this("ahmad://Sms/InvalidArgumentException", title)
    }

    class NullArgument internal constructor(title: String) :
        InvalidArgument("ahmad://Sms/InvalidArgument/NullArgumentException", title)

    open class WrappedException protected constructor(type: String, title: String) :
        SmsException(type, title) {

        internal constructor(title: String): this("ahmad://Sms/WrappedException", title)
    }

    companion object {
        fun invalidArg(message: String): InvalidArgument = InvalidArgument(message)
        fun nullArg(message: String): NullArgument = NullArgument(message)
        fun wrap(t: Throwable): SmsException = WrappedException(t.message ?: t.javaClass.canonicalName)
    }
}