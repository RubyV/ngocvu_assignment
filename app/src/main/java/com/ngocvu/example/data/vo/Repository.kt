package com.ngocvu.example.data.vo
import com.google.gson.annotations.SerializedName

data class Repository(
    val id: Int,
    val repositoryName: String,
    val desc: String,
)