package com.example.contestssubscription

import android.util.Log
import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ContestsViewModel() : ViewModel() {
    private var mutableLiveData: MutableLiveData<ArrayList<Contest>> =
        MutableLiveData(ArrayList<Contest>())

    fun getContests(): LiveData<ArrayList<Contest>> {
        if(mutableLiveData.value?.isEmpty() == true)
        getMyData()
        return mutableLiveData
    }

    fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(" https://codeforces.com/api/")
            .build()
            .create(CodeforcesApi::class.java)

        val retrofitData = retrofitBuilder.getContests()
        retrofitData.enqueue(object : Callback<ContestData?> {
            override fun onResponse(call: Call<ContestData?>, response: Response<ContestData?>) {
                d("Upcomming Contests", "got Data")
                val respBody = response.body()?.result!!
                mutableLiveData.value = respBody
            }

            override fun onFailure(call: Call<ContestData?>, t: Throwable) {
                Log.d("Upcoming Contests", t.message.toString())
            }
        })
    }

}