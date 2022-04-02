package com.hir0mu.cleanarchitecture.di

import com.hir0mu.cleanarchitecture.domain.mapper.ArticleBusinessModelMapper
import com.hir0mu.cleanarchitecture.domain.mapper.ArticleListBusinessModelMapper
import com.hir0mu.cleanarchitecture.domain.repository.ArticleRepository
import com.hir0mu.cleanarchitecture.domain.repository.FavoriteRepository
import com.hir0mu.cleanarchitecture.domain.usecase.article.FetchArticleDetailUsecase
import com.hir0mu.cleanarchitecture.domain.usecase.article.FetchArticleDetailUsecaseImpl
import com.hir0mu.cleanarchitecture.domain.usecase.article.FetchArticleListUsecase
import com.hir0mu.cleanarchitecture.domain.usecase.article.FetchArticleListUsecaseImpl
import com.hir0mu.cleanarchitecture.domain.usecase.favorite.FetchFavoriteArticleListUsecase
import com.hir0mu.cleanarchitecture.domain.usecase.favorite.FetchFavoriteArticleListUsecaseImpl
import com.hir0mu.cleanarchitecture.domain.usecase.favorite.ToggleFavoriteUsecase
import com.hir0mu.cleanarchitecture.domain.usecase.favorite.ToggleFavoriteUsecaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {
    @Singleton
    @Provides
    fun provideArticleMapper(): ArticleBusinessModelMapper {
        return ArticleBusinessModelMapper()
    }

    @Singleton
    @Provides
    fun provideFavoriteArticleMapper(): ArticleListBusinessModelMapper {
        return ArticleListBusinessModelMapper()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object UsecaseModule {
    @Provides
    fun provideFetchFavoriteArticleUsecase(
        favoriteRepository: FavoriteRepository,
        articleListMapper: ArticleListBusinessModelMapper,
        articleMapper: ArticleBusinessModelMapper
    ): FetchFavoriteArticleListUsecase {
        return FetchFavoriteArticleListUsecaseImpl(
            favoriteRepository = favoriteRepository,
            articleListMapper = articleListMapper,
            articleMapper = articleMapper
        )
    }

    @Provides
    fun provideToggleFavoriteUsecase(
        favoriteRepository: FavoriteRepository
    ): ToggleFavoriteUsecase {
        return ToggleFavoriteUsecaseImpl(
            favoriteRepository = favoriteRepository
        )
    }

    @Provides
    fun provideFetchArticleDetailUsecase(
        articleRepository: ArticleRepository,
        favoriteRepository: FavoriteRepository,
        articleMapper: ArticleBusinessModelMapper
    ): FetchArticleDetailUsecase {
        return FetchArticleDetailUsecaseImpl(
            articleRepository = articleRepository,
            favoriteRepository = favoriteRepository,
            articleMapper = articleMapper
        )
    }

    @Provides
    fun provideFetchArticleListUsecase(
        articleRepository: ArticleRepository,
        favoriteRepository: FavoriteRepository,
        articleListMapper: ArticleListBusinessModelMapper,
        articleMapper: ArticleBusinessModelMapper
    ): FetchArticleListUsecase {
        return FetchArticleListUsecaseImpl(
            articleRepository = articleRepository,
            favoriteRepository = favoriteRepository,
            articleMapper = articleMapper,
            articleListMapper = articleListMapper
        )
    }
}
