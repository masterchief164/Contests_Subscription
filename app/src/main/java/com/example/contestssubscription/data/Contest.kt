package com.example.contestssubscription.data

data class Contest(
    val duration: String,
    var end_time: String,
    val in_24_hours: String,
    val name: String,
    var start_time: String,
    val status: String,
    val url: String
)
