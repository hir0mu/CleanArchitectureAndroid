package com.hiring.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object TestUtils {
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://qiita.com/api/v2/")
        .addConverterFactory(
            Json { ignoreUnknownKeys = true }.asConverterFactory(
                checkNotNull(
                    MediaType.parse("application/json")
                )
            )
        )
        .build()
}
