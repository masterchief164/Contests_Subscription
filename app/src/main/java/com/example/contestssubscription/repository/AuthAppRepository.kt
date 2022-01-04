package com.example.contestssubscription.repository

import android.app.Application
import android.util.Log
import android.util.Log.e
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.contestssubscription.apis.ContestsApi
import com.example.contestssubscription.data.Contest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AuthAppRepository(private val application: Application) {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val userLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()
    private val loggedOutLiveData: MutableLiveData<Boolean> =
        if (firebaseAuth.currentUser != null) {
            userLiveData.value = firebaseAuth.currentUser
            MutableLiveData(false)
        } else
            MutableLiveData(true)
    private var contestsData: MutableLiveData<ArrayList<Contest>> =
        MutableLiveData(ArrayList())


    fun register(email: String, password: String) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userLiveData.value = (firebaseAuth.currentUser)
                    Toast.makeText(
                        application,
                        "Registration Successful",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    e("Registration", task.exception.toString())
                    Toast.makeText(
                        application,
                        "Registration Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
    }

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userLiveData.value = (firebaseAuth.currentUser)


                    Toast.makeText(
                        application,
                        "Login Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    e("Login", task.exception.toString())
                    Toast.makeText(
                        application,
                        "Login Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun logOut() {
        firebaseAuth.signOut()

        loggedOutLiveData.postValue(true)
        Toast.makeText(
            application,
            "Logout Successful",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun getContests(): LiveData<ArrayList<Contest>> {
        if (contestsData.value?.isEmpty() == true)
            getMyData()
//        getData()
        return contestsData
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser> {
        return userLiveData
    }

    fun getLoggedOutLiveData(): MutableLiveData<Boolean> {
        return loggedOutLiveData
    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://kontests.net/api/v1/")
            .build()
            .create(ContestsApi::class.java)

        var f1 = false
        var f2 = false
        var f3 = false

        val data: ArrayList<Contest> = ArrayList()

        val retrofitDataCodeForces = retrofitBuilder.getCodeforces()
        retrofitDataCodeForces.enqueue(object : Callback<ArrayList<Contest>?> {
            override fun onResponse(
                call: Call<ArrayList<Contest>?>,
                response: Response<ArrayList<Contest>?>
            ) {
                Log.d("Upcoming Contests", "got Data116")
                val respBody = response.body()!!
                data.addAll(respBody)
                f1 = true
            }

            override fun onFailure(call: Call<ArrayList<Contest>?>, t: Throwable) {
                e("Upcoming Contests", t.message.toString())
            }
        })


        val retrofitDataCodeChef = retrofitBuilder.getCodeChef()
        retrofitDataCodeChef.enqueue(object : Callback<ArrayList<Contest>?> {
            override fun onResponse(
                call: Call<ArrayList<Contest>?>,
                response: Response<ArrayList<Contest>?>
            ) {
                Log.d("Upcoming Contests", "got Data125")
                val resp = response.body()!!
                data.addAll(resp)
                f2 = true
            }

            override fun onFailure(call: Call<ArrayList<Contest>?>, t: Throwable) {
                e("Upcoming Contests", t.message.toString())
            }
        })

        val retrofitDataAtCoder = retrofitBuilder.getAtCoder()
        retrofitDataAtCoder.enqueue(object : Callback<ArrayList<Contest>?> {
            override fun onResponse(
                call: Call<ArrayList<Contest>?>,
                response: Response<ArrayList<Contest>?>
            ) {
                Log.d("Upcoming Contests", "got Data140")
                val resp = response.body()!!
                data.addAll(resp)
                f3 = true
            }

            override fun onFailure(call: Call<ArrayList<Contest>?>, t: Throwable) {
                e("Upcoming Contests", t.message.toString())
            }
        })

        GlobalScope.launch {
            while (true) {
                if (f1 && f2 && f3) {

                    contestsData.postValue(data)
                    for(x in data)
                        e("AUTH App" , x.url)
                    break
                }
            }
        }

    }

}