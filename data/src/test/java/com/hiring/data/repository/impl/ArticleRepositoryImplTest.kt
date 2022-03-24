package com.hiring.data.repository.impl

import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.domain.model.UserModel
import com.hiring.data.NetworkManager
import com.hiring.data.api.ArticleApi
import com.hiring.data.entity.ArticleEntity
import com.hiring.data.entity.ArticleGroupEntity
import com.hiring.data.entity.ArticleTagEntity
import com.hiring.data.entity.UserEntity
import com.hiring.data.mapper.ArticleModelMapper
import com.hiring.data.repository.ArticleRepositoryImpl
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

    @Mock
    private lateinit var networkManager: NetworkManager

    private lateinit var sut: ArticleRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sut = ArticleRepositoryImpl(api, networkManager, ArticleModelMapper())
    }

    @Test
    fun testGetArticleDetail() = runTest {
        // GIVEN
        val id = "id"
        given(api.articleDetail(id)).willReturn(ARTICLE)
        given(networkManager.isConnected).willReturn(true)

        // WHEN
        val result = sut.getArticleDetail(id)

        // THEN
        assertEquals(ARTICLE_MODEL, result)
        then(api).should().articleDetail(id)
    }


    @Test
    fun testGetArticles() = runTest {
        // GIVEN
        val itemId = "id"
        val page = 0
        val perPage = 20
        given(api.articles(itemId, page, perPage)).willReturn(ARTICLES)
        given(networkManager.isConnected).willReturn(true)

        // WHEN
        val result = sut.getArticles(itemId, page, perPage)

        // THEN
        assertEquals(ARTICLE_MODELS, result)
        then(api).should().articles(itemId, page, perPage)
    }

    companion object {
        private val GROUP = ArticleGroupEntity(
            createdAt = "createdAt",
            id = 0,
            name = "name",
            private = false,
            updatedAt = "updatedAt",
            urlName = "urlName",
        )

        private val TAG = ArticleTagEntity(
            name = "name",
            versions = listOf()
        )

        private val USER = UserEntity(
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

        private val ARTICLE = ArticleEntity(
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

        private val ARTICLE_MODEL = ArticleModel(
            id = "id",
            title = "title",
            url = "url",
            user = UserModel(
                id = "id",
                name = "name",
                profileImageUrl = "profileImageUrl",
            ),
        )

        private val ARTICLE_MODELS = listOf(ARTICLE_MODEL)
    }
}
