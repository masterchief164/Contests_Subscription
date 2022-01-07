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
import java.time.Instant
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


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

    fun getContests(
        codeforces: Boolean,
        codeChef: Boolean,
        atCoder: Boolean
    ): LiveData<ArrayList<Contest>> {
        if (contestsData.value?.isEmpty() == true)
            getMyData(codeforces, codeChef, atCoder)
        return contestsData
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser> {
        return userLiveData
    }

    fun getLoggedOutLiveData(): MutableLiveData<Boolean> {
        return loggedOutLiveData
    }

    private fun getMyData(codeforces: Boolean, codeChef: Boolean, atCoder: Boolean) {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://kontests.net/api/v1/")
            .build()
            .create(ContestsApi::class.java)

        var f1 = false
        var f2 = false
        var f3 = false

        val data: ArrayList<Contest> = ArrayList()

        if (codeforces) {
            val retrofitDataCodeForces = retrofitBuilder.getCodeforces()
            retrofitDataCodeForces.enqueue(object : Callback<ArrayList<Contest>?> {
                override fun onResponse(
                    call: Call<ArrayList<Contest>?>,
                    response: Response<ArrayList<Contest>?>
                ) {
                    val respBody = response.body()!!
                    data.addAll(respBody)
                    f1 = true
                }

                override fun onFailure(call: Call<ArrayList<Contest>?>, t: Throwable) {
                    e("Upcoming Contests", t.message.toString())
                    f1 = true
                }
            })
        } else
            f1 = true

        if (codeChef) {
            val retrofitDataCodeChef = retrofitBuilder.getCodeChef()
            retrofitDataCodeChef.enqueue(object : Callback<ArrayList<Contest>?> {
                override fun onResponse(
                    call: Call<ArrayList<Contest>?>,
                    response: Response<ArrayList<Contest>?>
                ) {
                    val resp = response.body()!!
                    data.addAll(resp)
                    f2 = true
                }

                override fun onFailure(call: Call<ArrayList<Contest>?>, t: Throwable) {
                    e("Upcoming Contests", t.message.toString())
                    f2 = true

                }
            })
        } else
            f2 = true

        if (atCoder) {
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
                    f3 = true
                }
            })
        } else
            f3 = true

        GlobalScope.launch {
            while (true) {
                if (f1 && f2 && f3) {
                    data.sortBy {it.start_time}
                    for (x in data) {
                        if (x.start_time[x.start_time.length - 1] == 'Z') {
                            x.start_time = parseDate(x.start_time)
                            x.end_time = parseDate(x.end_time)
                        } else {
                            x.start_time = transform(x.start_time)
                            x.start_time = parseDate(x.start_time)
                            x.end_time = transform(x.end_time)
                            x.end_time = parseDate(x.end_time)
                        }
                    }
                    contestsData.postValue(data)
                    f1 = false
                    f2 = f1
                    f3 = f1
                    break
                }
            }
        }

    }

    private fun transform(timeC: String):String{
        var time = timeC
        var tmp = time.indexOfFirst { it == ' ' }
        time = time.substring(
            0,
            tmp
        ) + "T" + time.substring(tmp + 1)
        tmp = time.indexOfLast { it == ' ' }
        time = time.substring(0, tmp) + ".000Z"
        return time
    }

    private fun parseDuration(time: String): String {
        val hours = time.toInt() / 3600
        val minutes = (time.toInt() % 3600) / 60
        return "$hours Hours $minutes minutes";
    }

    private fun parseDate(date: String): String {
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME
        val offsetDateTime: OffsetDateTime = OffsetDateTime.parse(date, timeFormatter)
        val parsedDate: Date = Date.from(Instant.from(offsetDateTime))
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = parsedDate
        var tmp = parsedDate.toString()
        tmp = tmp.substring(0, tmp.length - 18);
        return tmp;
    }

}