package com.codepunk.skeleton.data.remote.interceptor

import com.codepunk.skeleton.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class DiscogsUserAgentInterceptor @Inject constructor() : Interceptor {

    // region Methods

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request()
            .newBuilder()
            .header(HEADER_USER_AGENT, BuildConfig.DISCOGS_USER_AGENT)
            .build()
        return chain.proceed(newRequest)
    }

    // region Methods

    // region Companion object

    companion object {

        // region Variables

        private const val HEADER_USER_AGENT = "User-Agent"

        // endregion Variables

    }

    // endregion Companion object

}