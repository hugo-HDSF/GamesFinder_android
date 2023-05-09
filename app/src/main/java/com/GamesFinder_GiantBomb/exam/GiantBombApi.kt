package com.GamesFinder_GiantBomb.exam
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GiantBombApi {
    @GET("games/")
    fun searchGames(
        @Query("api_key") apiKey: String,
        @Query("filter") filter: String,
        @Query("format") format: String = "json"
    ): Call<ApiResult>

    @GET("user_reviews/")
    fun getGameComments(
        @Query("api_key") apiKey: String,
        @Query("format") format: String,
        @Query("field_list") fieldList: String,
        @Query("game") gameId: Int
    ): Call<ResponseBody>
}