package com.example.contestssubscription.data

import android.app.Application

class UserApplication : Application() {
    val database: UserDatabase by lazy { UserDatabase.getDatabase(this) }
}