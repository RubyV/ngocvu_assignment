package com.ngocvu.example.di

import android.app.Application
import androidx.room.Room
import com.ngocvu.example.data.vo.IssuesDatabase
import com.ngocvu.example.networking.GithubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Singleton
    @Provides
    fun provideWebService() = GithubApi()

    @Provides
    @Singleton
    fun provideDatabase(app: Application) : IssuesDatabase =
        Room.databaseBuilder(app, IssuesDatabase::class.java, "issues_database")
            .build()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope