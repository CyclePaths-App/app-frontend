package util

import com.cyclepaths.www.api.Error

enum class NetworkError : Error {
    BAD_REQUEST,
    UNAUTHORIZED,
    NOT_FOUND,
    NOT_AUTHORIZED,
    INTERNAL_ERROR,
    NOT_IMPLEMENTED,
}