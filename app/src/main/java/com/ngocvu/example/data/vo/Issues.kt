package com.ngocvu.example.data.vo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "issues")
data class Issues(
    @PrimaryKey val id: String,
    val title: String,
)