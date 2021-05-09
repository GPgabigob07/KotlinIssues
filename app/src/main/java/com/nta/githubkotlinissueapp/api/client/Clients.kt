package com.nta.githubkotlinissueapp.api.client

import com.google.gson.GsonBuilder
import com.nta.githubkotlinissueapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Clients {

    private val logInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val gson = GsonBuilder()
        .setLenient()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        .create()

    fun client() = OkHttpClient.Builder()
        .addNetworkInterceptor(logInterceptor)
        .addInterceptor { chain ->
            val newChain = chain
                .request()
                .newBuilder()
                .addHeader("Accept", "application/vnd.github.v3+json\"")
            chain.proceed(newChain.build())
        }
        .build()

    fun <T : Any> provideRetrofitAPI(retrofit: Retrofit, clazz: Class<T>): T =
        retrofit.create(clazz)

    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()


}
