package com.ngocvu.example.networking

import android.os.Looper
import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.ResponseField
import com.apollographql.apollo.api.cache.http.HttpCache
import com.apollographql.apollo.cache.http.ApolloHttpCache
import com.apollographql.apollo.cache.http.DiskLruHttpCacheStore
import com.apollographql.apollo.cache.normalized.CacheKey
import com.apollographql.apollo.cache.normalized.CacheKeyResolver
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import io.reactivex.rxjava3.schedulers.Schedulers.single
import okhttp3.OkHttpClient
import java.io.File

class GithubApi {
    fun getApolloClient(): ApolloClient {

        val cacheFactory = LruNormalizedCacheFactory(
            EvictionPolicy.builder().maxSizeBytes(10 * 1024 * 1024).build()
        )


        val resolver: CacheKeyResolver = object : CacheKeyResolver() {
            override fun fromFieldRecordSet(field: ResponseField, recordSet: Map<String, Any>): CacheKey {
                // Retrieve the id from the object itself
                return if (recordSet["__typename"] == "Repository") {
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
            //application interceptor
            //it->chain
            .addInterceptor {
                val original = it.request()
                val builder = original.newBuilder().method(
                    original.method(),
                    original.body()
                )
                builder.addHeader(
                    "Authorization",
                    "Bearer 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"
                )
                it.proceed(builder.build())
            }
            .build()

        // Control the cache policy
        val apolloClient = ApolloClient.builder()
            .serverUrl("https://api.github.com/graphql")
            .normalizedCache(cacheFactory, resolver)
            .defaultResponseFetcher(ApolloResponseFetchers
                .NETWORK_FIRST)
            .okHttpClient(okHttp)
            .build()

        return apolloClient
    }
}