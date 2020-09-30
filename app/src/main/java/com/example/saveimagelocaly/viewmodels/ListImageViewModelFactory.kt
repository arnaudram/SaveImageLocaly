package com.example.saveimagelocaly.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.saveimagelocaly.dao.ImageDao

class ListImageViewModelFactory(private val application: Application, private val imageDao: ImageDao):ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListImageViewModel::class.java)){
            return ListImageViewModel(application,imageDao)as T
        }
        else throw IllegalArgumentException("unexpected viewModel class")
    }
}