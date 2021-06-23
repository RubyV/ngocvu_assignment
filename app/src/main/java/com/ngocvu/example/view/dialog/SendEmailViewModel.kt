package com.ngocvu.example.view.dialog

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.IssuesListQuery
import com.example.OpenIssueMutation
import com.ngocvu.example.networking.GithubApi
import com.ngocvu.example.repository.GithubRepository
import com.ngocvu.example.view.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SendEmailViewModel @Inject constructor(
    private val issuesLst: GithubRepository,
) : ViewModel() {
    private val _newIssue by lazy { MutableLiveData<ViewState<Response<OpenIssueMutation.Data>>>() }
    val newIssue: MutableLiveData<ViewState<Response<OpenIssueMutation.Data>>>
        get() = _newIssue

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