package com.example.contestssubscription

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser


class LoggedInViewModel {
    private lateinit var authAppRepository: AuthAppRepository
    private lateinit var userLiveData: MutableLiveData<FirebaseUser>
    private lateinit var loggedOutLiveData: MutableLiveData<Boolean>

    fun LoggedInViewModel(application: Application) {
        authAppRepository = AuthAppRepository(application)
        userLiveData = authAppRepository.getUserLiveData()
        loggedOutLiveData = authAppRepository.getLoggedOutLiveData()
    }

    fun logOut() {
        authAppRepository.logOut()
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser> {
        return userLiveData
    }

    fun getLoggedOutLiveData(): MutableLiveData<Boolean> {
        return loggedOutLiveData
    }
}