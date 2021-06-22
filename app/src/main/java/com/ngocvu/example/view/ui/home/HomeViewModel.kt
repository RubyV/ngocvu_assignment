package com.ngocvu.example.view.ui.home

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.cache.normalized.NormalizedCache
import com.apollographql.apollo.exception.ApolloException
import com.example.IssuesListQuery
import com.ngocvu.example.data.vo.IssuesRepository
import com.ngocvu.example.networking.GithubApi
import com.ngocvu.example.repository.GithubRepository
import com.ngocvu.example.view.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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



}