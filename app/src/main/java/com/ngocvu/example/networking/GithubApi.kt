package com.ngocvu.example.networking

import android.os.Looper
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.cache.http.HttpCachePolicy
import com.apollographql.apollo.cache.http.ApolloHttpCache
import com.apollographql.apollo.cache.http.DiskLruHttpCacheStore
import com.ngocvu.example.view.ui.popular_movie.MainActivity
import hilt_aggregated_deps._com_ngocvu_example_view_ui_popular_movie_MainActivity_GeneratedInjector
import okhttp3.OkHttpClient
import java.io.File
import kotlin.coroutines.coroutineContext

class GithubApi {
    fun getApolloClient(): ApolloClient {
        // Directory where cached responses will be stored
        val file = File(
            getApplicationContext(),
            "apolloCache"
        )

        // Size in bytes of the cache
        val size: Long = 1024 * 1024

        // Create the http response cache store

        val cacheStore = DiskLruHttpCacheStore(file, size)

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
        val apolloClient = ApolloClient.builder()
            .serverUrl("https://api.github.com/graphql")
            .httpCache(ApolloHttpCache(cacheStore))
            .okHttpClient(okHttp)
            .build()

        // Control the cache policy
        val query = FeedQuery(limit = 10, type = FeedType.HOT)
        val dataResponse = apolloClient.query(query)
            .httpCachePolicy(HttpCachePolicy.NETWORK_FIRST)
            .toDeferred()
            .await()
        return apolloClient
    }
}