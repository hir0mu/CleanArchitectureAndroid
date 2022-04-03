package com.hir0mu.cleanarchitecture.di

import android.content.Context
import androidx.room.Room
import com.hir0mu.cleanarchitecture.BuildConfig
import com.hir0mu.cleanarchitecture.domain.mapper.ArticleBusinessModelMapper
import com.hir0mu.cleanarchitecture.domain.repository.ArticleRepository
import com.hir0mu.cleanarchitecture.domain.repository.FavoriteRepository
import com.hir0mu.cleanarchitecture.util.NetworkManagerImpl
import com.hir0mu.cleanarchitecture.data.NetworkManager
import com.hir0mu.cleanarchitecture.data.api.ArticleApi
import com.hir0mu.cleanarchitecture.data.api.local.ArticleLocal
import com.hir0mu.cleanarchitecture.data.db.ArticleDao
import com.hir0mu.cleanarchitecture.data.db.ArticleDataBase
import com.hir0mu.cleanarchitecture.data.mapper.ArticleModelMapper
import com.hir0mu.cleanarchitecture.data.repository.ArticleRepositoryImpl
import com.hir0mu.cleanarchitecture.data.repository.FavoriteRepositoryImpl
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
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
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
        return when (BuildConfig.LOCAL) {
            true -> ArticleLocal()
            else -> retrofit.create(ArticleApi::class.java)
        }
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
        articleDao: ArticleDao,
        articleMapper: ArticleModelMapper
    ): FavoriteRepository {
        return FavoriteRepositoryImpl(
            articleDao = articleDao,
            articleMapper = articleMapper
        )
    }

    @Singleton
    @Provides
    fun provideArticleRepository(
        api: ArticleApi,
        networkManager: NetworkManager,
        articleMapper: ArticleModelMapper
    ): ArticleRepository {
        return ArticleRepositoryImpl(
            api = api,
            networkManager = networkManager,
            articleMapper = articleMapper
        )
    }

    @Singleton
    @Provides
    fun provideNetworkManager(): NetworkManager {
        return NetworkManagerImpl
    }
}

@Module
@InstallIn(SingletonComponent::class)
object ModelMapperModule {
    @Singleton
    @Provides
    fun provideArticleModelMapper(): ArticleModelMapper {
        return ArticleModelMapper()
    }
}
