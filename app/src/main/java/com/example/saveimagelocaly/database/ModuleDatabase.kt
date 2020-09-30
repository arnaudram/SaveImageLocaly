package com.example.saveimagelocaly.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.saveimagelocaly.dao.ImageDao
import com.example.saveimagelocaly.data.Image

@Database(entities = [Image::class],version = 1,exportSchema = false)
 abstract class ModuleDatabase:RoomDatabase() {
 abstract val imageDao:ImageDao



 }
 private lateinit var singleDatabase: ModuleDatabase
fun getSingleDatabase(context:Context): ModuleDatabase {
    synchronized(ModuleDatabase::class.java){
        if (!::singleDatabase.isInitialized){
            singleDatabase= Room.databaseBuilder(context,ModuleDatabase::class.java,"mydatabase")
                .fallbackToDestructiveMigration().build()
        }
    }

    return singleDatabase
}