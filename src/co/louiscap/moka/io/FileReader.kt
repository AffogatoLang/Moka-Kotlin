package co.louiscap.moka.io

abstract class FileReader<out T>(protected val path: String) {
    abstract fun load(): T;
}