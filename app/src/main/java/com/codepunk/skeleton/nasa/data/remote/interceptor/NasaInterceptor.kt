package com.codepunk.skeleton.nasa.data.remote.interceptor

import com.codepunk.skeleton.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NasaInterceptor @Inject constructor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val httpUrl = chain
            .request()
            .url
            .newBuilder()
            .addQueryParameter("format", "json")
//             .addQueryParameter("api_key", BuildConfig.NASA_API_KEY)
            .build()
        val request = chain
            .request()
            .newBuilder()
            .url(httpUrl)
            .build()
        return chain.proceed(request)
    }

}
