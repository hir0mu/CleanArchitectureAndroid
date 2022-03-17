package com.hiring.data.repository.impl

import com.hiring.data.api.ArticleApi
import com.hiring.data.entity.Article
import com.hiring.data.entity.ArticleGroup
import com.hiring.data.entity.ArticleTag
import com.hiring.data.entity.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given
import org.mockito.kotlin.then

@ExperimentalCoroutinesApi
class ArticleRepositoryImplTest {

    @Mock
    private lateinit var api: ArticleApi
    private lateinit var sut: ArticleRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = ArticleRepositoryImpl(api)
    }

    @Test
    fun testGetArticleDetail() = runTest {
        // GIVEN
        val id = "id"
        given(api.articleDetail(id)).willReturn(ARTICLE)

        // WHEN
        val result = sut.getArticleDetail(id)

        // THEN
        assertEquals(ARTICLE, result)
        then(api).should().articleDetail(id)
    }


    @Test
    fun testGetArticles() = runTest {
        // GIVEN
        val itemId = "id"
        val page = 0
        val perPage = 20
        given(api.articles(itemId, page, perPage)).willReturn(ARTICLES)

        // WHEN
        val result = sut.getArticles(itemId, page, perPage)

        // THEN
        assertEquals(ARTICLES, result)
        then(api).should().articles(itemId, page, perPage)
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
