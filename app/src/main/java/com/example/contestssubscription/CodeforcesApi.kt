package com.example.contestssubscription

import retrofit2.Call
import retrofit2.http.GET

interface CodeforcesApi {
    @GET("contest.list")
    fun getContests(): Call<ContestData>

}