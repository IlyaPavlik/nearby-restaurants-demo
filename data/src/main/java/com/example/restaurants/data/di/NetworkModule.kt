package com.example.restaurants.data.di

import com.example.restaurants.data.network.FoursquareService
import com.example.restaurants.data.network.interceptor.AuthInterceptor
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.foursquare.com/v2/venues/"

    @Provides
    @Singleton
    fun provideFoursquareService(retrofitBuilder: Retrofit.Builder): FoursquareService =
        retrofitBuilder
            .baseUrl(BASE_URL)
            .build()
            .create(FoursquareService::class.java)

    @Provides
    @Singleton
    fun provideJacksonObjectMapper(): ObjectMapper = jacksonObjectMapper()
        .apply {
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)
            registerModule(KotlinModule())
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(authInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        httpClient: OkHttpClient,
        objectMapper: ObjectMapper
    ): Retrofit.Builder = Retrofit.Builder()
        .client(httpClient)
        .addConverterFactory(JacksonConverterFactory.create(objectMapper))

}