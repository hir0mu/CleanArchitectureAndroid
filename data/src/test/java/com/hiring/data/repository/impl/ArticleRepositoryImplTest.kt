package com.hiring.data.repository.impl

import com.hiring.data.api.ArticleApi
import com.hiring.data.entity.Article
import com.hiring.data.entity.ArticleGroup
import com.hiring.data.entity.ArticleTag
import com.hiring.data.entity.User
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.then
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ArticleRepositoryImplTest {
  private lateinit var sut: ArticleRepositoryImpl

  @Test
  fun testGetArticleDetail() {
    // GIVEN
    val id = "id"
    val api = mock<ArticleApi> {
      onBlocking { articleDetail(id) } doReturn ARTICLE
    }
    sut = ArticleRepositoryImpl(api)

    // WHEN
    val result = runBlocking { sut.getArticleDetail(id) }

    // THEN
    assertEquals(ARTICLE, result)
    runBlocking { then(api).should().articleDetail(id) }
  }


  @Test
  fun testGetArticles() {
    // GIVEN
    val itemId = "id"
    val page = 0
    val perPage = 20
    val api = mock<ArticleApi> {
      onBlocking { articles(itemId, page, perPage) } doReturn ARTICLES
    }
    sut = ArticleRepositoryImpl(api)

    // WHEN
    val result = runBlocking { sut.getArticles(itemId, page, perPage) }

    // THEN
    assertEquals(ARTICLES, result)
    runBlocking { then(api).should().articles(itemId, page, perPage) }
  }

  companion object {
    private val GROUP = ArticleGroup(
        createdAt = "createdAt",
        id = 0,
        name = "name",
        private = false,
        updatedAt = "updatedAt",
        urlName = "urlName",
    )

    private val TAG = ArticleTag(
        name = "name",
        versions = listOf()
    )

    private val USER = User(
        description = "description",
        facebookId = "facebookId",
        followeesCount = 0,
        followersCount = 0,
        githubLoginName = "githubLoginName",
        id = "id",
        itemsCount = 0,
        linkedinId = "linkedinId",
        location = "location",
        name = "name",
        organization = "organization",
        permanentId = 0,
        profileImageUrl = "profileImageUrl",
        teamOnly = false,
        twitterScreenName = "twitterScreenName",
        websiteUrl = "websiteUrl"
    )

    private val ARTICLE = Article(
        renderedBody = "renderedBody",
        body = "body",
        coediting = false,
        commentsCount = 0,
        createdAt = "createdAt",
        group = GROUP,
        id = "id",
        likesCount = 0,
        private = false,
        reactionsCount = 0,
        tags = listOf(TAG),
        title = "title",
        updatedAt = "updatedAt",
        url = "url",
        user = USER,
        pageViewsCount = 0
    )

    private val ARTICLES = listOf(ARTICLE)
  }
}
