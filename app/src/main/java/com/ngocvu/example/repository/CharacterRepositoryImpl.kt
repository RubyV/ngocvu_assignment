package com.ngocvu.example.repository

import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.example.CharactersListQuery
import com.ngocvu.example.networking.RickAndMortyApi
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val webService: RickAndMortyApi
) : CharacterRepository {
    override suspend fun queryCharacterList(): Response<CharactersListQuery.Data> {
        return webService.getApolloClient().query(CharactersListQuery()).await()
    }
}