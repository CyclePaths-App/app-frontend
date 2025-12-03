// Got the code from https://medium.com/@adman.shadman/sharing-application-context-in-kotlin-multiplatform-with-expect-actual-mechanism-88feb9649c14
package com.cyclepaths.www

import android.app.Application
import android.content.Context

object AppContext {
    private lateinit var application: Application

    fun setUp(context: Context) {
        application = context as Application
    }

    fun get(): Context {
        if (::application.isInitialized.not())
            throw Exception("Context isn't initialized")
        else return application.applicationContext
    }
}