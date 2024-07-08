package com.codepunk.skeleton.di.module

import android.content.Context
import arrow.retrofit.adapter.either.EitherCallAdapterFactory
import com.codepunk.skeleton.BuildConfig
import com.codepunk.skeleton.data.remote.interceptor.DiscogsAuthorizationInterceptor
import com.codepunk.skeleton.data.remote.interceptor.DiscogsUserAgentInterceptor
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebService
import com.codepunk.skeleton.data.remote.webservice.DiscogsWebserviceV2
import com.codepunk.skeleton.di.qualifier.Discogs
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
    @Discogs
    fun provideDiscogsOkHttpClient(
        cache: Cache,
        discogsAuthorizationInterceptor: DiscogsAuthorizationInterceptor,
        discogsUserAgentInterceptor: DiscogsUserAgentInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(discogsAuthorizationInterceptor)
        .addInterceptor(discogsUserAgentInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
        prettyPrint = true
        coerceInputValues = true
    }

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
        @Discogs client: OkHttpClient,
        eitherCallAdapterFactory: EitherCallAdapterFactory,
        converterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.DISCOGS_BASE_URL)
        .addCallAdapterFactory(eitherCallAdapterFactory)
        .addConverterFactory(converterFactory)
        .build()

    @Singleton
    @Provides
    fun provideDiscogsWebService(
        retrofit: Retrofit
    ): DiscogsWebService = retrofit.create(DiscogsWebService::class.java)

    @Singleton
    @Provides
    fun provideDiscogsWebServiceV2(
        retrofit: Retrofit
    ): DiscogsWebserviceV2 = retrofit.create(DiscogsWebserviceV2::class.java)

}
