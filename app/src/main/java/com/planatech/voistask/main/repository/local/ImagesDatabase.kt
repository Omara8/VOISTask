package com.planatech.voistask.main.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.planatech.voistask.main.model.Image
import com.planatech.voistask.main.model.RemoteKeys

@Database(
    entities = [Image::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class ImagesDatabase : RoomDatabase() {
    abstract fun imagesDao(): ImagesDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {

        @Volatile
        private var INSTANCE: ImagesDatabase? = null

        fun getInstance(context: Context): ImagesDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ImagesDatabase::class.java, "vois.db"
            )
                .build()
    }
}