package com.GamesFinder_GiantBomb.exam

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://www.giantbomb.com/api/"

    fun create(): GiantBombApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(GiantBombApi::class.java)
    }
}
