package com.hiring.data.api

import com.hiring.data.entity.Article
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleApi {
    @GET("tags/{itemId}/items")
    suspend fun articles(
        @Path("itemId") itemId: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<Article>

    @GET("items/{id}")
    suspend fun articleDetail(
        @Path("id") id: String
    ): Article
}
