package com.cyclepaths.www

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform