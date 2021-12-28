package com.example.contestssubscription.data

data class Contest(
    val name: String,
    val startTimeSeconds: Int,
    val durationSeconds: Int,
    val frozen: Boolean,
    val id: Int,
    val phase: String,
    val relativeTimeSeconds: Int,
    val type: String
)
