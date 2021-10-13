package com.planatech.voistask.main.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.planatech.voistask.main.model.Image
import com.planatech.voistask.main.model.RemoteKeys
import com.planatech.voistask.main.repository.local.ImagesDatabase
import com.planatech.voistask.main.repository.remote.ImagesAPI
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ImagesRemoteMediator(
    private val service: ImagesAPI,
    private val imagesDatabase: ImagesDatabase
) : RemoteMediator<Int, Image>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Image>
    ): MediatorResult {
        val position = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }
        try {
            val apiResponse = service.getImagesResult(position)

            val items = apiResponse
            val endOfPaginationReached = items.isEmpty()
            imagesDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    imagesDatabase.imagesDao().clearItems()
                }
                val prevKey = if (position == 1) null else position - 1
                val nextKey = if (endOfPaginationReached) null else position + 1
                val keys = items.map {
                    RemoteKeys(itemId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                imagesDatabase.remoteKeysDao().insertAll(keys)
                imagesDatabase.imagesDao().insertAll(items)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Image>): RemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { item ->
                imagesDatabase.remoteKeysDao().remoteKeysRepoId(item.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Image>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { item ->
                imagesDatabase.remoteKeysDao().remoteKeysRepoId(item.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Image>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { itemId ->
                imagesDatabase.remoteKeysDao().remoteKeysRepoId(itemId)
            }
        }
    }

}