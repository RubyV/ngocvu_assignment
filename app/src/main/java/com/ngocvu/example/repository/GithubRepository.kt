package com.ngocvu.example.repository

import com.apollographql.apollo.api.Response
import com.example.*
import com.example.fragment.IssuesFragment

interface GithubRepository {
    suspend fun getAllRepositories() : Response<RepositoryListQuery.Data>

    suspend fun gelAllIssuesList(): Response<IssuesListQuery.Data>

    suspend fun addNewComment(issuesId: String, body: String) : Response<AddCommentToIssueMutation.Data>

    suspend fun openIssues(repositoryId: String, title:String) : Response<OpenIssueMutation.Data>

    suspend fun closedIssues(issuesId: String): Response<CloseIssueMutation.Data>

    suspend fun mapIssueRepositoriesResponseToRepositories(response: Response<IssuesListQuery.Data>): List<IssuesFragment>
}