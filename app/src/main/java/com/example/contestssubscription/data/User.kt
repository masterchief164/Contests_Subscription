package com.example.contestssubscription.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @ColumnInfo(name = "UID") @PrimaryKey
    val uid: String="0",
    @ColumnInfo(name = "User Name")
    val userName: String = "Name",
    @ColumnInfo(name = "Email")
    val email: String = "example@example.com",
    @ColumnInfo(name = "Codeforces")
    var codeforces: Boolean = true,
    @ColumnInfo(name = "CodeChef")
    var codeChef: Boolean = true,
    @ColumnInfo(name = "AtCoder")
    var atCoder: Boolean = true
)