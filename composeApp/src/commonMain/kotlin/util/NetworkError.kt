package util

enum class NetworkError : Error {
    // HTTP Error Codes:
    BAD_REQUEST,    // 400
    UNAUTHORIZED,   // 401
    NOT_FOUND,      // 404
    INTERNAL_ERROR, // 500
    NOT_IMPLEMENTED,// 501

    // Other errors:
    NOT_CONNECTED,  // Not connected to wifi
    SERIALIZATION_ERROR,  // Couldn't parse JSON response
    UNKNOWN,
    NO_INTERNET,
    BACKEND_DOWN
}