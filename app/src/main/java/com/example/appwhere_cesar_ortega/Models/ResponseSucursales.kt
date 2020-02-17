package com.example.app_where.Models

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

data class ResponseSucursales (@SerializedName("status") val status : Int,
                               @SerializedName("description") val description : String,
                               @SerializedName("merchants") val merchants : JsonArray

)