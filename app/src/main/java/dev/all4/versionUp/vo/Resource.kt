package dev.all4.versionUp.vo

/**
 * Created by Livio Lopez on 11/11/20.
 */

//sealed class Resource<out T> {
//    class Loading<out T> : Resource<T>()
//    data class Success<out T>(val data: T) : Resource<T>()
//    data class Exception<out T>(val throwable: Throwable) : Resource<T>()
//}

sealed class Resource<T> {
    abstract val data: T?
    data class Loading<T>(val hasStarted: Boolean = false, override val data: T? = null) : Resource<T>()
    data class Success<T>(override val data: T? = null) : Resource<T>()
    data class Exception<T>(val throwable: Throwable, override val data: T? = null) : Resource<T>()
}