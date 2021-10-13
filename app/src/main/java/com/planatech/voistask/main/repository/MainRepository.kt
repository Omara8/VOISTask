package com.planatech.voistask.main.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.planatech.voistask.main.model.Image
import com.planatech.voistask.main.repository.local.ImagesDatabase
import com.planatech.voistask.main.repository.remote.ImagesAPI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    val imagesAPI: ImagesAPI,
    val imagesDatabase: ImagesDatabase
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getImages(): Flow<PagingData<Image>> {
        val pagingSourceFactory = {
            imagesDatabase.imagesDao().getImages()
        }

        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            remoteMediator = ImagesRemoteMediator(
                imagesAPI,
                imagesDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

}