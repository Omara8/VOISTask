package com.planatech.voistask.main.di

import android.content.Context
import com.planatech.voistask.main.repository.MainRepository
import com.planatech.voistask.main.repository.local.ImagesDatabase
import com.planatech.voistask.main.repository.remote.ImagesAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class ImagesModelModule {

    @Provides
    @ViewModelScoped
    fun provideMainRepository(imagesAPI: ImagesAPI, imagesDatabase: ImagesDatabase) =
        MainRepository(imagesAPI, imagesDatabase)

    @Provides
    @ViewModelScoped
    fun provideImagesAPI(retrofit: Retrofit): ImagesAPI = retrofit.create(
        ImagesAPI::class.java
    )
}

@Module
@InstallIn(SingletonComponent::class)
class ProductionModule {

    @Singleton
    @Provides
    fun provideImagesDatabase(@ApplicationContext appContext: Context): ImagesDatabase {
        return ImagesDatabase.getInstance(appContext)
    }
}