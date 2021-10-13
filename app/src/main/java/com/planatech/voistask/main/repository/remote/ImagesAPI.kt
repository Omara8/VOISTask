package com.planatech.voistask.main.repository.remote

import com.planatech.voistask.main.model.Image
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImagesAPI {

    @GET("list")
    suspend fun getImagesResult(@Query("page") page: Int): List<Image>

}