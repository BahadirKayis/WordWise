package com.bahadir.wordle.di

import com.bahadir.wordle.dispatcher.DefaultDispatcherProvider
import com.bahadir.wordle.dispatcher.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CoroutinesModule {

    @Provides
    @Singleton
    fun provideCoroutineDispatcherIO(): CoroutineDispatcher = Dispatchers.IO


    @Provides
    @Singleton
    fun provideCoroutineDispatcherDefault(): DispatcherProvider = DefaultDispatcherProvider()

}