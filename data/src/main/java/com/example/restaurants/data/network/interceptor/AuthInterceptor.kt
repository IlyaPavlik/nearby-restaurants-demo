package com.example.restaurants.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val authUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(CLIENT_ID_KEY, CLIENT_ID)
            .addQueryParameter(CLIENT_SECRET_KEY, CLIENT_SECRET)
            .addQueryParameter(API_VERSION_KEY, API_VERSION)
            .build()

        val authRequest = originalRequest.newBuilder()
            .url(authUrl)
            .build()

        return chain.proceed(authRequest)
    }

    companion object {

        //TODO Warning! Saving keys such way is not correct. Using for the demo only.
        private const val CLIENT_ID_KEY = "client_id"
        private const val CLIENT_ID = "01UCGJPSV15T1JAWGAML2EHTRMULSNGTEFX3IDZH1CJFYWTJ"
        private const val CLIENT_SECRET_KEY = "client_secret"
        private const val CLIENT_SECRET = "T5ZIFZTL3EPTQMJRNAFA3R3WBMM15FJROVY0YNBYRZT32S3N"
        private const val API_VERSION_KEY = "v"
        private const val API_VERSION = "20211017"

    }
}