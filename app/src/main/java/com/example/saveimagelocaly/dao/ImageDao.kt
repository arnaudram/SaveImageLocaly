package com.example.saveimagelocaly.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.saveimagelocaly.data.Image

@Dao
interface ImageDao {
    @Insert
    fun insertImage(image: Image)
    @Delete
    fun deleteImage(image: Image)
    @Update
    fun updateImage(image: Image)
    @Query("SELECT * FROM image")
     fun getAllImages():LiveData<List<Image>>
}