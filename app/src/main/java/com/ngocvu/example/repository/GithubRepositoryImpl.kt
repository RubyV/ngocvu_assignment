package com.ngocvu.example.repository

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.example.*
import com.example.fragment.IssuesFragment
import com.ngocvu.example.networking.GithubApi

import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(private val apiService: GithubApi): GithubRepository {

    override suspend fun getAllRepositories(): Response<RepositoryListQuery.Data> {
        return apiService.getApolloClient().query(RepositoryListQuery()).await()
    }

    override suspend fun gelAllIssuesList(): Response<IssuesListQuery.Data> {

        return apiService.getApolloClient()
            .query(IssuesListQuery())
            .await()
    }

    override suspend fun addNewComment(issuesId: String, body: String): Response<AddCommentToIssueMutation.Data> {
        return apiService.getApolloClient().mutate(AddCommentToIssueMutation(issuesId, body)).await()
    }

    override suspend fun openIssues(
        repositoryId: String,
        title: String
    ): Response<OpenIssueMutation.Data> {
        return apiService.getApolloClient().mutate(OpenIssueMutation(repositoryId, title)).await()
    }

    override suspend fun closedIssues(issuesId: String): Response<CloseIssueMutation.Data> {
        return apiService.getApolloClient().mutate(CloseIssueMutation(issuesId)).await()

    }

    override suspend fun mapIssueRepositoriesResponseToRepositories(response: Response<IssuesListQuery.Data>): List<IssuesFragment> {
        return response.data?.repository?.issues?.nodes?.mapNotNull { it?.fragments?.issuesFragment } ?: emptyList()
    }

}