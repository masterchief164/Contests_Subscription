package com.example.contestssubscription.data


import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Query("SELECT * from user WHERE uid = :uid")
    fun getUser(uid: String): User

}