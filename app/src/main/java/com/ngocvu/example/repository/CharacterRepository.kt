package com.ngocvu.example.repository

import com.apollographql.apollo.api.Response
import com.example.CharactersListQuery

interface CharacterRepository {
    suspend fun queryCharacterList(): Response<CharactersListQuery.Data>

}