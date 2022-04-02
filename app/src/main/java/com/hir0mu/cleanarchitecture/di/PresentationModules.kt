package com.hir0mu.cleanarchitecture.di

import com.hir0mu.cleanarchitecture.view.ViewModelArgs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

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
