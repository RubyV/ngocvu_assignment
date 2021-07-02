package com.ngocvu.example.view.ui.home


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.IssuesListQuery
import com.example.OpenIssueMutation
import com.ngocvu.example.repository.GithubRepository
import com.ngocvu.example.view.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

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



    fun queryIssues(): Observable<Response<IssuesListQuery.Data>> {
        return issuesLst.gelAllIssuesListRx()
    }



}