package com.hiring.cleanarchitecture.di

import android.content.Context
import androidx.room.Room
import com.hiring.cleanarchitecture.domain.mapper.ArticleMapper
import com.hiring.cleanarchitecture.domain.mapper.FavoriteArticleMapper
import com.hiring.cleanarchitecture.domain.usecase.ArticleDetailUsecase
import com.hiring.cleanarchitecture.domain.usecase.ArticleListUsecase
import com.hiring.cleanarchitecture.domain.usecase.FavoriteArticleUsecase
import com.hiring.cleanarchitecture.domain.usecase.impl.ArticleDetailUsecaseImpl
import com.hiring.cleanarchitecture.domain.usecase.impl.ArticleListUsecaseImpl
import com.hiring.cleanarchitecture.domain.usecase.impl.FavoriteArticleUsecaseImpl
import com.hiring.data.api.ArticleApi
import com.hiring.data.db.ArticleDao
import com.hiring.data.db.ArticleDataBase
import com.hiring.data.repository.ArticleRepository
import com.hiring.data.repository.FavoriteRepository
import com.hiring.data.repository.impl.ArticleRepositoryImpl
import com.hiring.data.repository.impl.FavoriteRepositoryImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl("https://qiita.com/api/v2/")
            .client(okHttpClient)
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(contentType))
            .build()
    }

    @Singleton
    @Provides
    fun provideArticleApi(
        retrofit: Retrofit
    ): ArticleApi {
        return retrofit.create(ArticleApi::class.java)
    }

    @Singleton
    @Provides
    fun provideArticleDao(
        @ApplicationContext context: Context
    ): ArticleDao {
        return Room.databaseBuilder(
            context,
            ArticleDataBase::class.java, "article"
        )
            .build().articleDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideFavoriteRepository(
        articleDao: ArticleDao
    ): FavoriteRepository {
        return FavoriteRepositoryImpl(
            articleDao = articleDao
        )
    }

    @Singleton
    @Provides
    fun provideArticleRepository(
        api: ArticleApi
    ): ArticleRepository {
        return ArticleRepositoryImpl(
            api = api
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {
    @Singleton
    @Provides
    fun provideArticleMapper(): ArticleMapper {
        return ArticleMapper()
    }

    @Singleton
    @Provides
    fun provideFavoriteArticleMapper(): FavoriteArticleMapper {
        return FavoriteArticleMapper()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object UsecaseModule {
    @Singleton
    @Provides
    fun provideFavoriteArticleUsecase(
        favoriteRepository: FavoriteRepository,
        favoriteArticleMapper: FavoriteArticleMapper
    ): FavoriteArticleUsecase {
        return FavoriteArticleUsecaseImpl(
            favoriteRepository = favoriteRepository,
            favoriteArticleMapper = favoriteArticleMapper
        )
    }

    @Singleton
    @Provides
    fun provideArticleDetailUsecase(
        articleRepository: ArticleRepository,
        favoriteRepository: FavoriteRepository,
        articleMapper: ArticleMapper
    ): ArticleDetailUsecase {
        return ArticleDetailUsecaseImpl(
            articleRepository = articleRepository,
            favoriteRepository = favoriteRepository,
            articleMapper = articleMapper
        )
    }

    @Singleton
    @Provides
    fun provideArticleListUsecase(
        articleRepository: ArticleRepository,
        favoriteRepository: FavoriteRepository,
        articleMapper: ArticleMapper
    ): ArticleListUsecase {
        return ArticleListUsecaseImpl(
            articleRepository = articleRepository,
            favoriteRepository = favoriteRepository,
            articleMapper = articleMapper
        )
    }
}
