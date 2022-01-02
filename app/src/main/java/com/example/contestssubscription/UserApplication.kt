package com.example.contestssubscription

import android.app.Application
import com.example.contestssubscription.data.UserDatabase

class UserApplication:Application() {
    val database: UserDatabase by lazy { UserDatabase.getDatabase(this) }
}