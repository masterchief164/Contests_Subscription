package com.example.contestssubscription.viewModels

import android.util.Log.e
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.contestssubscription.data.User
import com.example.contestssubscription.data.UserDao
import kotlinx.coroutines.launch

class UserSitesViewModel(private val userDao: UserDao) : ViewModel() {

    private fun insertUser(user: User) {
        viewModelScope.launch {
            userDao.insert(user)
        }
    }

    private fun getNewUserEntry(
        userName: String,
        uid: String,
        email: String,
        codeforces: Boolean,
        atCoder: Boolean,
        codeChef: Boolean
    ): User {
        return User(
            userName = userName,
            uid = uid,
            email = email,
            codeChef = codeChef,
            atCoder = atCoder,
            codeforces = codeforces
        )
    }


    fun retrieveUser(id: String): User {
        return userDao.getUser(id)
    }

    fun updateUserData(user: User) {
        updateUser(user)
    }

    private fun updateUser(user: User) {
        viewModelScope.launch {
            userDao.update(user)
        }
    }

    fun addNewUser(
        userName: String,
        uid: String,
        email: String,
        codeforces: Boolean,
        atCoder: Boolean,
        codeChef: Boolean
    ) {
        val newUser = getNewUserEntry(userName, uid, email, codeforces, atCoder, codeChef)
        insertUser(newUser)
    }


}

class UserSitesViewModelFactory(private val userDao: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserSitesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserSitesViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}