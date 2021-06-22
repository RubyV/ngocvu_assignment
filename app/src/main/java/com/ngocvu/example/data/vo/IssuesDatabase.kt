package com.ngocvu.example.data.vo

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ngocvu.example.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider


@Database(entities = [Issues::class], version = 1)
abstract class IssuesDatabase : RoomDatabase() {

    abstract fun issuesDao(): IssuesDao

    class Callback @Inject constructor(
        private val database: Provider<IssuesDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().issuesDao()

            applicationScope.launch {
                dao.insertIssues(Issues("Issues1", "Issues 11"))
                dao.insertIssues(Issues("Issues2", "Issues 22"))

            }
        }
    }
}