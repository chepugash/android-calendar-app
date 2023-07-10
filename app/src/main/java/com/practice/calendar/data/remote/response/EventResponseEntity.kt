package com.practice.calendar.data.remote.response

import com.google.gson.annotations.SerializedName

class EventResponseEntity(
    @SerializedName("date_finish")
    val dateFinish: String,
    @SerializedName("date_start")
    val dateStart: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
