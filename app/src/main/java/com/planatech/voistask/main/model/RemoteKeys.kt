package com.planatech.voistask.main.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val itemId: Int,
    val prevKey: Int?,
    val nextKey: Int?
) : Serializable