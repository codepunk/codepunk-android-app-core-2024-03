package com.codepunk.skeleton

import android.app.Application
import com.codepunk.skeleton.core.loginator.Handleinator
import com.codepunk.skeleton.core.loginator.Loginator
import dagger.hilt.android.HiltAndroidApp
import java.util.logging.Level
import javax.inject.Inject

@HiltAndroidApp
class CodepunkApplication : Application() {

    @Inject
    lateinit var handleinator: Handleinator

    override fun onCreate() {
        super.onCreate()
        Loginator
            .removeHandlers()
            .addHandler(handleinator)
            .level = Level.ALL
    }

}
