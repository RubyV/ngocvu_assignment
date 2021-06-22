package com.ngocvu.example.view.ui.issusedetails

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.AddCommentToIssueMutation
import com.ngocvu.example.repository.GithubRepository
import com.ngocvu.example.view.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class IssueCommentViewModel  @Inject constructor(
    private val addNewComment: GithubRepository,
) : ViewModel() {
    private val _addComment by lazy { MutableLiveData<ViewState<Response<AddCommentToIssueMutation.Data>>>() }
    val addComment: MutableLiveData<ViewState<Response<AddCommentToIssueMutation.Data>>>
        get() = _addComment

    fun addNewComment(body: String) = viewModelScope.launch {
        _addComment.postValue(ViewState.Loading())
        try {
            val response = addNewComment.addNewComment(
                "MDU6SXNzdWU5MjU3NjAzNzA=",
                body
            )
            Log.d("Git", response.toString())
            _addComment.postValue(ViewState.Success(response))
        } catch (e: ApolloException) {
            Log.d("ApolloException", "Failure", e)
            _addComment.postValue(ViewState.Error("Error fetching characters"))
        }
    }

    fun postComment(body: String, ) = viewModelScope.launch {
        _addComment.postValue(ViewState.Loading())
        try {
            val response = addNewComment.addNewComment(
                "MDU6SXNzdWU5MjU3NjAzNzA=",
                body
            )
            Log.d("Git4", response.toString())
            _addComment.postValue(ViewState.Success(response))
        } catch (e: ApolloException) {
            Log.d("ApolloException", "Failure", e)
            _addComment.postValue(ViewState.Error("Error fetching characters"))
        }
    }
}