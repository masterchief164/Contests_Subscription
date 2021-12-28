package com.example.contestssubscription.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sites")
data class Site(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name="Site Name")
    val siteName: String = "Test",
    @ColumnInfo(name = "Enabled Status")
    val enabled: Boolean = true
)