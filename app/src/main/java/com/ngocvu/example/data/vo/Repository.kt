package com.ngocvu.example.data.vo

data class Repository(
    val createdAt: String,
    val forkCount: Int,
    val id: String,
    val issues: IssuesX,
    val name: String
)