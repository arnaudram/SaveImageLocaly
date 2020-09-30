package com.example.saveimagelocaly.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.saveimagelocaly.dao.ImageDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

class ListImageViewModel(application: Application, private val  imageDao:ImageDao) :AndroidViewModel(application) {
    private val uiScope= CoroutineScope(Dispatchers.Main + Job())
val images=imageDao.getAllImages()


    override fun onCleared() {
        super.onCleared()
        uiScope.cancel()
    }
}