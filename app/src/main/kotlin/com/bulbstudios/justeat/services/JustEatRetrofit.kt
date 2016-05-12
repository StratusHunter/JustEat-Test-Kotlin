package com.bulbstudios.justeat.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Terence Baker on 12/05/2016.
 */
class JustEatRetrofit {

    companion object {

        val JSON_RESTAURANTS = "Restaurants"

        private val JUST_EAT_URL = "https://public.je-apis.com"
        private val AUTH_CODE = "VGVjaFRlc3RBUEk6dXNlcjI="

        private val httpClient = {

            var client = OkHttpClient.Builder()
            client.addInterceptor {
                chain ->

                val request = chain.request().newBuilder()
                        .addHeader("Accept-Tenant", "uk")
                        .addHeader("Accept-Language", "en-GB")
                        .addHeader("Accept-Charset", "utf-8")
                        .addHeader("Authorization", "Basic $AUTH_CODE")
                        .addHeader("User-Agent", "Android")
                        .addHeader("Host", "public.je-apis.com")
                        .build()

                chain.proceed(request)
            }

            client.build()
        }()

        private val retrofit = {

            Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(JUST_EAT_URL)
                    .client(httpClient)
                    .build()
        }()

        val resturantService = retrofit.create(JustEatService::class.java)
    }
}