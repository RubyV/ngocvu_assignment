package com.ngocvu.example.data.vo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface IssuesDao {

    @Query("SELECT * FROM issues")
    fun getAllIssues(): Flow<List<Issues>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIssues(issues: Issues)

    @Query("DELETE FROM issues")
    suspend fun deleteAllIssues()
}