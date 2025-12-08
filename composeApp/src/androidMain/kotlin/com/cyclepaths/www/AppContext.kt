// Got the code from https://medium.com/@adman.shadman/sharing-application-context-in-kotlin-multiplatform-with-expect-actual-mechanism-88feb9649c14
package com.cyclepaths.www

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.core.app.ActivityCompat

object AppContext {
    private lateinit var application: Application
    private lateinit var activity: Activity

    fun setContext(context: Context) {
        application = context as Application
    }

    fun setActivity(activity: Activity) {
        this.activity = activity
    }

    fun getContext(): Context {
        if (::application.isInitialized.not())
            throw Exception("Context isn't initialized")
        else return application.applicationContext
    }

    fun getActivity(): Activity {
        if (::activity.isInitialized.not()) throw Exception("Acticvity isn't initialized.")
        else return activity
    }
}