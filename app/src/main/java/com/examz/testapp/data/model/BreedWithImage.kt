package com.examz.testapp.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class BreedWithImage(
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)