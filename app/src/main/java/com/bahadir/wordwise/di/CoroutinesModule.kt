package com.bahadir.wordwise.di

import com.bahadir.wordwise.dispatcher.DefaultDispatcherProvider
import com.bahadir.wordwise.dispatcher.DispatcherProvider
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