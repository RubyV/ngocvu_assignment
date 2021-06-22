package com.ngocvu.example.data.vo

import android.util.Log
import androidx.room.withTransaction
import com.ngocvu.example.networking.GithubApi
import com.ngocvu.example.repository.GithubRepository
import com.ngocvu.example.utils.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class IssuesRepository  @Inject constructor(
    private val api: GithubRepository,
    private val db: IssuesDatabase
) {
    private val issuesDao = db.issuesDao()

    fun getIssues() = networkBoundResource(
        query = {
            issuesDao.getAllIssues()
        },
        fetch = {
            delay(2000)
            api.gelAllIssuesList()
        },
        saveFetchResult = { issues ->
            Log.d("Git1", issues.toString())
            db.withTransaction {
                issuesDao.deleteAllIssues()
            }
        }
    )
}