package com.example.contestssubscription.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.contestssubscription.data.Contest
import com.example.contestssubscription.repository.AuthAppRepository
import com.google.firebase.auth.FirebaseUser


class LoggedInViewModel(application: Application) : AndroidViewModel(application) {
    private var authAppRepository: AuthAppRepository = AuthAppRepository(application)
    private var userLiveData: MutableLiveData<FirebaseUser> = authAppRepository.getUserLiveData()
    private var loggedOutLiveData: MutableLiveData<Boolean> =
        authAppRepository.getLoggedOutLiveData()

    fun logOut() {
        authAppRepository.logOut()
    }

    fun getContests(codeforces:Boolean,codeChef: Boolean,atCoder:Boolean): LiveData<ArrayList<Contest>> {
        return authAppRepository.getContests(codeforces,codeChef,atCoder)
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser> {
        return userLiveData
    }

    fun getLoggedOutLiveData(): MutableLiveData<Boolean> {
        return loggedOutLiveData
    }
}