package com.example.restaurants.data.network

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class FoursquareServiceProvider {

    fun provideFoursquareService(): FoursquareService {
        val objectMapper = getJacksonObjectMapper()
        val okHttpClient = buildOkHttpClient()
        val retrofit = buildRetrofitBuilder(okHttpClient, objectMapper)
        return retrofit
            .baseUrl(BASE_URL)
            .build()
            .create(FoursquareService::class.java)
    }

    private fun getJacksonObjectMapper(): ObjectMapper = jacksonObjectMapper()
        .apply {
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)
            registerModule(KotlinModule())
        }

    private fun buildOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(buildAuthInterceptor())
            .build()
    }

    private fun buildRetrofitBuilder(
        httpClient: OkHttpClient,
        objectMapper: ObjectMapper
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
    }

    private fun buildAuthInterceptor(): Interceptor = object : Interceptor {
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
    }

    companion object {

        private const val BASE_URL = "https://api.foursquare.com/v2/venues/"
        private const val CLIENT_ID_KEY = "client_id"
        private const val CLIENT_ID = "01UCGJPSV15T1JAWGAML2EHTRMULSNGTEFX3IDZH1CJFYWTJ"
        private const val CLIENT_SECRET_KEY = "client_secret"
        private const val CLIENT_SECRET = "T5ZIFZTL3EPTQMJRNAFA3R3WBMM15FJROVY0YNBYRZT32S3N"
        private const val API_VERSION_KEY = "v"
        private const val API_VERSION = "20211017"

    }

}