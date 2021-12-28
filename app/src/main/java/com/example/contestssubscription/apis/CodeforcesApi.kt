package com.example.contestssubscription.apis

import com.example.contestssubscription.data.ContestData
import retrofit2.Call
import retrofit2.http.GET

interface CodeforcesApi {
    @GET("contest.list")
     fun getContests(): Call<ContestData>

}