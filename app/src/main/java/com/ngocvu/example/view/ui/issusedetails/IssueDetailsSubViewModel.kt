package com.ngocvu.example.view.ui.issusedetails

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.AddCommentToIssueMutation
import com.example.CloseIssueMutation
import com.ngocvu.example.repository.GithubRepository
import com.ngocvu.example.view.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class IssueDetailsSubViewModel@Inject constructor(
    private val repo: GithubRepository,
) : ViewModel() {
    private val _closeIssue by lazy { MutableLiveData<ViewState<Response<CloseIssueMutation.Data>>>() }
    val closeIssue: MutableLiveData<ViewState<Response<CloseIssueMutation.Data>>>
        get() = _closeIssue

    fun closeIssue(id: String) = viewModelScope.launch {
        _closeIssue.postValue(ViewState.Loading())
        try {
            val response = repo.closedIssues(id)
            Log.d("Git", response.toString())
            _closeIssue.postValue(ViewState.Success(response))
        } catch (e: ApolloException) {
            Log.d("ApolloException", "Failure", e)
            _closeIssue.postValue(ViewState.Error("Error fetching characters"))
        }
    }
}
