package com.ngocvu.example.view.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.AddCommentToIssueMutation
import com.example.IssuesListQuery
import com.example.OpenIssueMutation
import com.ngocvu.example.networking.GithubApi
import com.ngocvu.example.repository.GithubRepository
import com.ngocvu.example.view.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject


//

@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
   private val issuesLst: GithubRepository,
   private val apolloClient: GithubApi,
) : ViewModel() {
    private val _issuesList by lazy { MutableLiveData<ViewState<Response<IssuesListQuery.Data>>>() }
    val issuseList: MutableLiveData<ViewState<Response<IssuesListQuery.Data>>>
        get() = _issuesList
    private val _newIssue by lazy { MutableLiveData<ViewState<Response<OpenIssueMutation.Data>>>() }
    val newIssue: MutableLiveData<ViewState<Response<OpenIssueMutation.Data>>>
        get() = _newIssue


    fun queryIssueList() = viewModelScope.launch {
        _issuesList.postValue(ViewState.Loading())

        try {
            val response = issuesLst.gelAllIssuesList()
            _issuesList.postValue(ViewState.Success(response))

        } catch (e: ApolloException) {
            Log.d("ApolloException", "Failure", e)
            _issuesList.postValue(ViewState.Error("Error fetching issues"))
        }

    }

    fun createNewIssue(title: String) = viewModelScope.launch {
        _newIssue.postValue(ViewState.Loading())

        try {
            val response = issuesLst.openIssues("MDEwOlJlcG9zaXRvcnkzNzgyMzExOTQ=",title)
            Log.d("Git6", response.toString())
            _newIssue.postValue(ViewState.Success(response))

        } catch (e: ApolloException) {
            Log.d("ApolloException", "Failure", e)
            _newIssue.postValue(ViewState.Error("Error fetching issues"))
        }
    }



}