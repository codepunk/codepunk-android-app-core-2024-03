package com.codepunk.skeleton.nasa.di.module

import android.content.Context
import arrow.retrofit.adapter.either.EitherCallAdapterFactory
import com.codepunk.skeleton.BuildConfig
import com.codepunk.skeleton.nasa.data.remote.interceptor.NasaInterceptor
import com.codepunk.skeleton.nasa.di.qualifier.Nasa
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Singleton
    @Provides
    fun provideCache(@ApplicationContext context: Context): Cache =
        Cache(context.cacheDir, BuildConfig.OK_HTTP_CLIENT_CACHE_SIZE)

    @Singleton
    @Provides
    @Nasa
    fun provideNasaOkHttpClient(
        cache: Cache,
        nasaInterceptor: NasaInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(nasaInterceptor)
        // TODO .addInterceptor(urlOverrideInterceptor) ?
        // TODO .addInterceptor(authorizationInterceptor) ?
        .build()

    @Singleton
    @Provides
    fun provideJson(): Json = Json { ignoreUnknownKeys = true }

    @Singleton
    @Provides
    fun provideEitherCallAdapterFactory(): EitherCallAdapterFactory = EitherCallAdapterFactory()

    @Singleton
    @Provides
    fun provideConverterFactory(networkJson: Json): Converter.Factory =
        networkJson.asConverterFactory("application/json".toMediaType())

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        eitherCallAdapterFactory: EitherCallAdapterFactory,
        converterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.NASA_BASE_URL)
        .addCallAdapterFactory(eitherCallAdapterFactory)
        .addConverterFactory(converterFactory)
        .build()

}
