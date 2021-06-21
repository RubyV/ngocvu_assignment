package com.ngocvu.example.repository

import com.apollographql.apollo.api.Response
import com.example.AddCommentToIssueMutation
import com.example.IssuesListQuery
import com.example.RepositoryListQuery

interface GithubRepository {
    suspend fun getAllRepositories() : Response<RepositoryListQuery.Data>


    suspend fun gelAllIssuesList(): Response<IssuesListQuery.Data>

    suspend fun addNewComment(issuesId: String, body: String) : Response<AddCommentToIssueMutation.Data>
}