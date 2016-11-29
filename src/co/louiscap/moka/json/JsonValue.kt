package co.louiscap.moka.json

interface ReadableJsonValue<out T> {
    fun value(): T;
}

interface WritableJsonValue<in T> {
    fun value(value: T);
}

data class JsonValue<out T>(private val _value: T) : ReadableJsonValue<T> {
    override fun value() = _value
}

data class MutableJsonValue<T>(private var _value: T) : ReadableJsonValue<T>, WritableJsonValue<T> {
    override fun value() = _value
    override fun value(value: T) { _value = value }
}