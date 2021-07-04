package com.ngocvu.example.networking

import android.content.Context
import android.os.Looper
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.ResponseField
import com.apollographql.apollo.cache.normalized.CacheKey
import com.apollographql.apollo.cache.normalized.CacheKeyResolver
import com.apollographql.apollo.cache.normalized.sql.BuildConfig
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.ngocvu.example.BuildConfig.PERSONAL_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import javax.inject.Inject



class GithubApi @Inject constructor(
    @ApplicationContext private val context: Context,
){
    companion object {
        var BASE_URL = "https://api.github.com/graphql"
    }
    fun getApolloClient (): ApolloClient {

        val sqlNormalizedCacheFactory = SqlNormalizedCacheFactory(context,"github_cache")
        val cacheKeyResolver = object : CacheKeyResolver() {
            override fun fromFieldRecordSet(field: ResponseField, recordSet: Map<String, Any>): CacheKey {
                return if (recordSet["__typename"] == "Issue") {
                    CacheKey(recordSet["id"] as String)
                } else {
                    CacheKey.NO_KEY
                }
            }

            override fun fromFieldArguments(field: ResponseField, variables: Operation.Variables): CacheKey {
                return CacheKey.NO_KEY
            }
        }
        check(Looper.myLooper() == Looper.getMainLooper()) {
            "Only the main thread can get the apolloClient instance"
        }
        val okHttp = OkHttpClient.Builder()
            .addInterceptor {
                val original = it.request()
                val builder = original.newBuilder().method(
                    original.method(),
                    original.body()
                )
                builder.addHeader(
                    "Authorization",
                    "Bearer ${PERSONAL_TOKEN}"
                )
                it.proceed(builder.build())
            }
            .build()

        // Control the cache policy
        val apolloClient = ApolloClient.builder()
            .serverUrl(BASE_URL)
            .normalizedCache(sqlNormalizedCacheFactory, cacheKeyResolver)
            .defaultResponseFetcher(ApolloResponseFetchers
                .CACHE_FIRST)
            .okHttpClient(okHttp)
            .build()

        return apolloClient
    }
}
