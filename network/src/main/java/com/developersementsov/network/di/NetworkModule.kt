package com.developersementsov.network.di

import android.util.Log
import com.developersementsov.network.BuildConfig
import com.developersementsov.network.CocktailBarService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

@Module
class NetworkModule {
    companion object {
        private const val HOST = "www.thecocktaildb.com/api/json/v1/1"
        private const val SERVER_URL = "https://$HOST/"
        private const val TAG = "NetworkModule"
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        val trustManager = object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                Log.d(TAG, "checkClientTrusted")
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                Log.d(TAG, "checkServerTrusted")
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                Log.d(TAG, "getAcceptedIssuers")
                return arrayOf()
            }
        }

        val sslSocketFactory = with(SSLContext.getInstance("TLSv1.2")) {
            init(null, arrayOf(trustManager), SecureRandom())
            socketFactory
        }

        val builder = OkHttpClient.Builder()
        builder.interceptors().clear()
        interceptLogging(builder)

//        builder.hostnameVerifier(HostnameVerifier { hostName, _ ->
//            hostName == HOST
//        })
//        builder.sslSocketFactory(sslSocketFactory, trustManager)
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
}