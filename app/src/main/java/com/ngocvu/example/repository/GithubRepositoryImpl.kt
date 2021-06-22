package com.ngocvu.example.repository

import android.util.Log
import androidx.room.withTransaction
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.cache.http.HttpCachePolicy
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.example.*
import com.ngocvu.example.data.vo.Issues
import com.ngocvu.example.data.vo.IssuesDatabase
import com.ngocvu.example.networking.GithubApi
import com.ngocvu.example.utils.Resource
import com.ngocvu.example.utils.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val webService: GithubApi,
    private val db: IssuesDatabase
): GithubRepository {

    private val issuesDao = db.issuesDao()

    override suspend fun getAllRepositories(): Response<RepositoryListQuery.Data> {
        return webService.getApolloClient().query(RepositoryListQuery()).await()
    }

    override suspend fun gelAllIssuesList(): Response<IssuesListQuery.Data> {
        return webService.getApolloClient()
            .query(IssuesListQuery())
            .await()
    }

    override suspend fun addNewComment(issuesId: String, body: String): Response<AddCommentToIssueMutation.Data> {
        return webService.getApolloClient().mutate(AddCommentToIssueMutation(issuesId, body)).await()
    }

    override suspend fun openIssues(
        repositoryId: String,
        body: String
    ): Response<OpenIssueMutation.Data> {
        return webService.getApolloClient().mutate(OpenIssueMutation(repositoryId, body)).await()
    }

    override suspend fun closedIssues(issuesId: String): Response<CloseIssueMutation.Data> {
        return webService.getApolloClient().mutate(CloseIssueMutation(issuesId)).await()

    }



}