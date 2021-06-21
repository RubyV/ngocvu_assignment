package com.ngocvu.example.view.ui.issusellist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.IssuesListQuery
import com.example.RepositoryListQuery
import com.ngocvu.example.repository.GithubRepository
import com.ngocvu.example.view.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class IssuesListViewModel @Inject constructor(
    private val issuesLst: GithubRepository,
) : ViewModel() {
    private val _issuesList by lazy { MutableLiveData<ViewState<Response<IssuesListQuery.Data>>>() }
    val issuseList: MutableLiveData<ViewState<Response<IssuesListQuery.Data>>>
        get() = _issuesList

    fun queryIssueList() = viewModelScope.launch {
        _issuesList.postValue(ViewState.Loading())
        try {
            val response = issuesLst.gelAllIssuesList()
            _issuesList.postValue(ViewState.Success(response))
        } catch (e: ApolloException) {
            Log.d("ApolloException", "Failure", e)
            _issuesList.postValue(ViewState.Error("Error fetching characters"))
        }
    }
}