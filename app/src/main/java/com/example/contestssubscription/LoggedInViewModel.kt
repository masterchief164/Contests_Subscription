package com.example.contestssubscription

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser


class LoggedInViewModel(application: Application) : AndroidViewModel(application) {
    private var authAppRepository: AuthAppRepository = AuthAppRepository(application)
    private var userLiveData: MutableLiveData<FirebaseUser> = authAppRepository.getUserLiveData()
    private var loggedOutLiveData: MutableLiveData<Boolean> =
        authAppRepository.getLoggedOutLiveData()
    private var contestsData: LiveData<ArrayList<Contest>> = authAppRepository.getContests()

    fun logOut() {
        authAppRepository.logOut()
    }

    fun getContests(): LiveData<ArrayList<Contest>> {
        return contestsData
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser> {
        return userLiveData
    }

    fun getLoggedOutLiveData(): MutableLiveData<Boolean> {
        return loggedOutLiveData
    }
}