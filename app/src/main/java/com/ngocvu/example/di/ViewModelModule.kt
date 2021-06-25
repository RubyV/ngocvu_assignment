package com.ngocvu.example.di

import android.app.Application
import android.content.Context
import com.ngocvu.example.repository.GithubRepository
import com.ngocvu.example.repository.GithubRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

    @Binds
    @ViewModelScoped
    abstract fun bindRepository(repo: GithubRepositoryImpl): GithubRepository



}