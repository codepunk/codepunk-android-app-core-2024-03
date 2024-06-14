package com.codepunk.skeleton.di.module

import com.codepunk.skeleton.BuildConfig
import com.codepunk.skeleton.core.loginator.Formatinator
import com.codepunk.skeleton.core.loginator.Handleinator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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

}
