package com.example.contestssubscription

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser





class LoginRegisterViewModel:ViewModel() {
    private var authAppRepository: AuthAppRepository = AuthAppRepository(Application())
    private var userLiveData:MutableLiveData<FirebaseUser> = authAppRepository.getUserLiveData()

    fun login(email: String, password: String) {
        authAppRepository.login(email, password)
    }

    fun register(email: String, password: String) {
        authAppRepository.register(email, password)
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser> {
        return userLiveData
    }
}