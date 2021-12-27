package com.example.contestssubscription

import android.app.Application
import android.util.Log.e
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class AuthAppRepository(private val application: Application) {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val userLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()
    private val loggedOutLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        if (firebaseAuth.currentUser != null) {
            userLiveData.postValue(firebaseAuth.currentUser)
            loggedOutLiveData.postValue(false)
        }
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser> {
        return userLiveData
    }

    fun getLoggedOutLiveData(): MutableLiveData<Boolean> {
        return loggedOutLiveData
    }

    fun register(email: String, password: String) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userLiveData.postValue(firebaseAuth.currentUser);
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
    }

}