package com.example.coinpage.data.network

import com.example.coinpage.data.model.Coin
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinService {

    @GET("coins/markets?vs_currency=usd&order=market_cap_desc")
    suspend fun getCoins(
        @Query("per_page") perPage: Int = 20,
        @Query("page") page: Int = 1,
    ): List<Coin>


    companion object {
        private const val BASE_URL = "https://api.coingecko.com/api/v3/"

        fun create(): CoinService {
            val interceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build().create(CoinService::class.java)

        }

    }

}