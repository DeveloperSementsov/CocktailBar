package com.developersementsov.network.di

import com.developersementsov.network.BuildConfig
import com.developersementsov.network.CocktailBarService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.interceptors().clear()
        interceptLogging(builder)
        return builder.build()
    }

    private fun interceptLogging(builder: OkHttpClient.Builder) {
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.interceptors().add(httpLoggingInterceptor)
        }
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(SERVER_URL)
        .client(okHttpClient)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .serializeNulls()
                    .setDateFormat("yyyy-MM-dd")
                    .create()
            )
        )
        .build()

    @Provides
    @Singleton
    internal fun provideCocktailService(retrofit: Retrofit): CocktailBarService =
        retrofit.create(CocktailBarService::class.java)

    companion object {
        private const val HOST = "www.thecocktaildb.com/api/json/v1/1"
        private const val SERVER_URL = "https://$HOST/"
    }
}