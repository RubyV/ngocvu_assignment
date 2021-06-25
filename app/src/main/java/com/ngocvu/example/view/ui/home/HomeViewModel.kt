package com.ngocvu.example.view.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.example.AddCommentToIssueMutation
import com.example.IssuesListQuery
import com.example.OpenIssueMutation
import com.example.fragment.IssuesFragment
import com.google.android.datatransport.runtime.dagger.Provides
import com.ngocvu.example.networking.GithubApi
import com.ngocvu.example.repository.GithubRepository
import com.ngocvu.example.view.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.*
import okhttp3.internal.platform.Platform.get
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


//

@ExperimentalCoroutinesApi
@HiltViewModel

class HomeViewModel @Inject constructor(
    private val issuesLst: GithubRepository,
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
            _issuesList.postValue(ViewState.Error("Error fetching issues"))
        }

    }

    fun createNewIssue(title: String) = viewModelScope.launch {
        _newIssue.postValue(ViewState.Loading())
        try {
            val response = issuesLst.openIssues("MDEwOlJlcG9zaXRvcnkzNzgyMzExOTQ=",title)
            _newIssue.postValue(ViewState.Success(response))

        } catch (e: ApolloException) {
            _newIssue.postValue(ViewState.Error("Error create issues"))
        }
    }




}