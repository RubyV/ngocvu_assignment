package com.ngocvu.example.repository
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.rx3.rxQuery
import com.example.*
import com.example.fragment.IssuesFragment
import com.ngocvu.example.networking.GithubApi
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
class GithubRepositoryImpl

@Inject constructor(private val apiService: GithubApi): GithubRepository {

    override fun gelAllIssuesListRx(): Observable<Response<IssuesListQuery.Data>> {
        val res = apiService.getApolloClient()
            .rxQuery(IssuesListQuery())
        return res
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