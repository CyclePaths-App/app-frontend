package util

/*
Modified from a repository from @philipplackner at https://github.com/philipplackner/CMP-Ktor/blob/master/composeApp/src/commonMain/kotlin/util/Result.kt
 */

sealed interface Result<out D, out E : Error> {
    data class Ok<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : util.Error>(val error: E) : Result<Nothing, E>
}

inline fun <D, E : Error, R> Result<D, E>.map(map: (D) -> R): Result<R, E> {
    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Ok -> Result.Ok(map(data))
    }
}

fun <D, E : Error> Result<D, E>.asEmptyDataResult(): EmptyResult<E> {
    return map { }
}

inline fun <D, E : Error> Result<D, E>.onSuccess(action: (D) -> Unit): Result<D, E> {
    return when (this) {
        is Result.Error -> this
        is Result.Ok -> {
            action(data)
            this
        }
    }
}

inline fun <D, E : Error> Result<D, E>.onError(action: (E) -> Unit): Result<D, E> {
    return when (this) {
        is Result.Error -> {
            action(error)
            this
        }

        is Result.Ok -> this
    }
}

typealias EmptyResult<E> = Result<Unit, E>