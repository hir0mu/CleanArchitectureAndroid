package com.hiring.cleanarchitecture.di

import android.content.Context
import androidx.room.Room
import com.hiring.cleanarchitecture.domain.mapper.ArticleMapper
import com.hiring.cleanarchitecture.domain.mapper.FavoriteArticleMapper
import com.hiring.cleanarchitecture.domain.usecase.article.FetchArticleDetailUsecase
import com.hiring.cleanarchitecture.domain.usecase.article.FetchArticleListUsecase
import com.hiring.cleanarchitecture.domain.usecase.article.FetchArticleDetailUsecaseImpl
import com.hiring.cleanarchitecture.domain.usecase.article.FetchArticleListUsecaseImpl
import com.hiring.cleanarchitecture.domain.usecase.favorite.*
import com.hiring.cleanarchitecture.util.NetworkManagerImpl
import com.hiring.cleanarchitecture.view.ViewModelArgs
import com.hiring.data.NetworkManager
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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
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
        api: ArticleApi,
        networkManager: NetworkManager
    ): ArticleRepository {
        return ArticleRepositoryImpl(
            api = api,
            networkManager = networkManager
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
    @Provides
    fun provideFetchFavoriteArticleUsecase(
        favoriteRepository: FavoriteRepository,
        favoriteArticleMapper: FavoriteArticleMapper
    ): FetchFavoriteArticleListUsecase {
        return FetchFavoriteArticleListUsecaseImpl(
            favoriteRepository = favoriteRepository,
            favoriteArticleMapper = favoriteArticleMapper
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
        articleMapper: ArticleMapper
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
        articleMapper: ArticleMapper
    ): FetchArticleListUsecase {
        return FetchArticleListUsecaseImpl(
            articleRepository = articleRepository,
            favoriteRepository = favoriteRepository,
            articleMapper = articleMapper
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Singleton
    @Provides
    @Named("DispatchersIO")
    fun provideDispatchersIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {
    @Singleton
    @Provides
    fun provideViewModelArgs(
        @Named("DispatchersIO") dispatcherIO: CoroutineDispatcher
    ): ViewModelArgs {
        return ViewModelArgs(
            dispatcherIO = dispatcherIO
        )
    }
}
