package com.example.saveimagelocaly.data


import android.graphics.Bitmap

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image")
 data class Image(
 @PrimaryKey(autoGenerate = true)
 var id:Int=0,
 var description:String,
 var picture:String
)
