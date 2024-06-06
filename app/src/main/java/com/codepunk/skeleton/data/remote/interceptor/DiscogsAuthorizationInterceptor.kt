package com.codepunk.skeleton.data.remote.interceptor

import com.codepunk.skeleton.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class DiscogsAuthorizationInterceptor @Inject constructor() : Interceptor {

    // region Methods

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request()
            .newBuilder()
            .header(
                name = AUTHORIZATION,
                value = "$DISCOGS_TOKEN=${BuildConfig.DISCOGS_PERSONAL_ACCESS_TOKEN}"
            )
            .build()
        return chain.proceed(newRequest)
    }

    // region Methods

    // region Companion object

    companion object {

        // region Variables

        private const val AUTHORIZATION = "Authorization"
        private const val DISCOGS_TOKEN = "Discogs token"

        // endregion Variables

    }

    // endregion Companion object

}