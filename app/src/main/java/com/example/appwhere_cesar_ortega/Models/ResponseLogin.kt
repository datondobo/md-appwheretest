package com.example.app_where.Models

import com.google.gson.annotations.SerializedName

//Respuesta LOGIN GSON
data class ResponseLogin (@SerializedName("description") val description: String,
                          @SerializedName("status") val status: Int,
                          @SerializedName("successful") val successful: Boolean,
                          @SerializedName("userId") val userId: String)
