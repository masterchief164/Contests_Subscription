package com.example.contestssubscription.apis

import com.example.contestssubscription.data.Contest
import com.example.contestssubscription.data.ContestData
import retrofit2.Call
import retrofit2.http.GET

interface ContestsApi {
    @GET("contest.list")
     fun getContests(): Call<ContestData>

     @GET("codeforces")
     fun getCodeforces(): Call<ArrayList<Contest>>

    @GET("at_coder")
    fun getAtCoder(): Call<ArrayList<Contest>>
    @GET("code_chef")
    fun getCodeChef(): Call<ArrayList<Contest>>

}