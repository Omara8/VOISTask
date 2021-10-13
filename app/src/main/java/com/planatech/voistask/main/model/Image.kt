package com.planatech.voistask.main.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "images")
data class Image(
    @PrimaryKey val id: Int,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    val download_url: String
) : Serializable