package com.example.contestssubscription

import android.app.Application
import android.util.Log
import android.util.Log.e
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AuthAppRepository(private val application: Application) {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val userLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()
    private val loggedOutLiveData: MutableLiveData<Boolean> = if (firebaseAuth.currentUser != null) {
        userLiveData.postValue(firebaseAuth.currentUser)
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
                    userLiveData.postValue(firebaseAuth.currentUser);
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
                    ).show();
                }

            }
    }

    fun login(email: String?, password: String?) {
        firebaseAuth.signInWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userLiveData.postValue(firebaseAuth.currentUser)

                    Toast.makeText(
                        application,
                        "Logout Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    e("Login", task.exception.toString())
                    Toast.makeText(
                        application,
                        "Login Failed",
                        Toast.LENGTH_SHORT
                    ).show();
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
        if(contestsData.value?.isEmpty() == true)
            getMyData()
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
            .baseUrl(" https://codeforces.com/api/")
            .build()
            .create(CodeforcesApi::class.java)

        val retrofitData = retrofitBuilder.getContests()
        retrofitData.enqueue(object : Callback<ContestData?> {
            override fun onResponse(call: Call<ContestData?>, response: Response<ContestData?>) {
                Log.d("Upcomming Contests", "got Data")
                val respBody = response.body()?.result!!
                contestsData.value = respBody
            }

            override fun onFailure(call: Call<ContestData?>, t: Throwable) {
                Log.d("Upcoming Contests", t.message.toString())
            }
        })
    }

}