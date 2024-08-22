package com.codepunk.skeleton

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.codepunk.skeleton.core.loginator.Handleinator
import com.codepunk.skeleton.core.loginator.Loginator
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch
import mozilla.components.lib.publicsuffixlist.PublicSuffixList
import java.util.logging.Level
import javax.inject.Inject

@HiltAndroidApp
class CodepunkApplication : Application() {

    @Inject
    lateinit var handleinator: Handleinator

    @Inject
    lateinit var publicSuffixList: PublicSuffixList

    override fun onCreate() {
        super.onCreate()
        Loginator
            .removeHandlers()
            .addHandler(handleinator)
            .level = Level.ALL

        // Prefetch the publix suffix list
        ProcessLifecycleOwner.get().lifecycleScope.launch {
            publicSuffixList.prefetch().await()
        }
    }

}
