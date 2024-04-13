package me.lab.happyprimo.data.network

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import me.lab.happyprimo.data.repositories.ServiceAPI
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

class ServiceClient {
    companion object {
        @JvmStatic
        fun createWithToken( androidContext: Context ,token: String,baseUrl:String): ServiceAPI {
            val logging = HttpInterceptor()
            logging.apply {
                level = HttpInterceptor.Level.BODY
            }
            val header =
                Interceptor { chain ->
                    chain.proceed(
                        chain.request().newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Authorization", token)
                            .build()
                    )
                }

            val okHttpBuilder = OkHttpClient.Builder()
                .addInterceptor(header)
                .addInterceptor(logging)
                .connectTimeout(2000, TimeUnit.SECONDS)
                .writeTimeout(2000, TimeUnit.SECONDS)
                .readTimeout(2000, TimeUnit.SECONDS)

            val retrofit = Retrofit.Builder()
                .addConverterFactory(
                    MoshiConverterFactory.create(
                    Moshi.Builder().add(
                        KotlinJsonAdapterFactory()
                    ).build()))
                .client(okHttpBuilder.build())
                .baseUrl(baseUrl)
                .build()
            return retrofit.create(ServiceAPI::class.java)
        }

        @JvmStatic
        fun create(androidContext: Context,baseUrl: String): ServiceAPI {
            val logging = HttpInterceptor()

            logging.apply {
                level = HttpInterceptor.Level.BODY
            }
            val header =
                Interceptor { chain ->
                    chain.proceed(
                        chain.request().newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .build()
                    )
                }

            val okHttpBuilder = OkHttpClient.Builder()
                .addInterceptor(header)
                .addInterceptor(logging)
                .connectTimeout(2000, TimeUnit.SECONDS)
                .writeTimeout(2000, TimeUnit.SECONDS)
                .readTimeout(2000, TimeUnit.SECONDS)

            val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(
                    Moshi.Builder().add(
                        KotlinJsonAdapterFactory()
                    ).build()))
                .client(okHttpBuilder.build())
                .baseUrl(baseUrl)
                .build()
            return retrofit.create(ServiceAPI::class.java)
        }

        fun createForXML(baseUrl: String): ServiceAPI {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val headerInterceptor = Interceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder()
                        .build()
                )
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(headerInterceptor)
                .connectTimeout(5000, TimeUnit.SECONDS)
                .writeTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(okHttpClient)
                .build()


            return retrofit.create(ServiceAPI::class.java)
        }

    }
}
