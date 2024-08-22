package com.codepunk.skeleton.di.module

import android.content.Context
import com.codepunk.skeleton.BuildConfig
import com.codepunk.skeleton.core.loginator.Formatinator
import com.codepunk.skeleton.core.loginator.Handleinator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mozilla.components.lib.publicsuffixlist.PublicSuffixList
import org.kefirsf.bb.BBProcessorFactory
import org.kefirsf.bb.TextProcessor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideHandleinator(): Handleinator {
        val appId = BuildConfig.APPLICATION_ID
        return Handleinator(Formatinator(appId))
    }

    @Singleton
    @Provides
    fun provideTextProcessor(): TextProcessor = BBProcessorFactory.getInstance().create()

    @Singleton
    @Provides
    fun providesPublicSuffixList(
        @ApplicationContext context: Context
    ): PublicSuffixList = PublicSuffixList(context)
}
