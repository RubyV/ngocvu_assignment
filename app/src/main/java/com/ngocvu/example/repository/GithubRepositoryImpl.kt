package com.ngocvu.example.repository

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.example.AddCommentToIssueMutation
import com.example.IssuesListQuery
import com.example.RepositoryListQuery
import com.ngocvu.example.networking.GithubApi
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val webService: GithubApi
): GithubRepository {
    override suspend fun getAllRepositories(): Response<RepositoryListQuery.Data> {
        return webService.getApolloClient().query(RepositoryListQuery()).await()
    }

    override suspend fun gelAllIssuesList(): Response<IssuesListQuery.Data> {
        return webService.getApolloClient().query(IssuesListQuery()).await()
    }

    override suspend fun addNewComment(issuesId: String, body: String): Response<AddCommentToIssueMutation.Data> {
        return webService.getApolloClient().mutate(AddCommentToIssueMutation(issuesId, body)).await()
    }

}