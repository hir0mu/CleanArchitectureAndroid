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
import com.hiring.cleanarchitecture.view.list.ArticleListFragment
import com.hiring.cleanarchitecture.view.list.ArticleListViewModel
import com.hiring.data.api.ArticleApi
import com.hiring.data.db.ArticleDao
import com.hiring.data.db.ArticleDataBase
import com.hiring.data.repository.ArticleRepository
import com.hiring.data.repository.FavoriteRepository
import com.hiring.data.repository.impl.ArticleRepositoryImpl
import com.hiring.data.repository.impl.FavoriteRepositoryImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

private val dataModule = module {
    single { okHttpClient() }
    single { retrofit(get()) }

    single { get<Retrofit>().create(ArticleApi::class.java) }
    single { articleDao(get()) }

    single<FavoriteRepository> { FavoriteRepositoryImpl(get()) }
    single<ArticleRepository> { ArticleRepositoryImpl(get()) }
}

private val domainModule = module {
    factory { ArticleMapper() }
    factory { FavoriteArticleMapper() }

    factory<FavoriteArticleUsecase> { FavoriteArticleUsecaseImpl(get(), get()) }
    factory<ArticleDetailUsecase> { ArticleDetailUsecaseImpl(get(), get(), get()) }
    factory<ArticleListUsecase> { ArticleListUsecaseImpl(get(), get(), get()) }
}

private val appModule = module {
    scope<ArticleListFragment> {
        viewModel { ArticleListViewModel(get()) }
    }
}

private fun okHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        .build()
}

private fun retrofit(client: OkHttpClient): Retrofit {
    val contentType = "application/json".toMediaType()
    return Retrofit.Builder()
        .baseUrl("https://qiita.com/api/v2/")
        .client(client)
        .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(contentType))
        .build()
}

private fun articleDao(applicationContext: Context): ArticleDao {
    return Room.databaseBuilder(
        applicationContext,
        ArticleDataBase::class.java, "article"
    ).build().articleDao()
}

val allModules = dataModule + domainModule + appModule
