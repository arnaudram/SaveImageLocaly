package com.example.saveimagelocaly.viewmodels

import android.app.Application

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.saveimagelocaly.dao.ImageDao
import com.example.saveimagelocaly.data.Image
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.broadcast
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


const val ACTUAL_BITMAP = "com.example.saveimagelocaly.actualBitmap"

class InsertImageViewModel(application: Application, val imageDao: ImageDao) :
    AndroidViewModel(application) {

    private val uiScope = CoroutineScope(Dispatchers.Main + Job())
    private var _onInsert = MutableLiveData<Boolean>()
    val onInsert: LiveData<Boolean>
        get() = _onInsert
    private var _bitmap = MutableLiveData<Bitmap?>()
    val bitmap: LiveData<Bitmap?>
        get() = _bitmap

    init {

        _onInsert.value=false
    }
    fun storeState(outState: Bundle) {
        // outState.putParcelable(ACTUAL_BITMAP,_bitmap.value)
        _bitmap.value?.let {
            outState.putString(ACTUAL_BITMAP, bitmapToString(it))
        }


    }

    fun insetImage(image: Image) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                imageDao.insertImage(image)
            }
        }
    }
    fun doneInserting(){
        _onInsert.value=false
    }

    var isNewlyCreated: Boolean = true

    fun bitmapToString(bitmap: Bitmap): String {

        var bos = ByteArrayOutputStream()
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        } catch (e: Exception) {
            Log.d(getApplication<Application>()::class.java.simpleName, "${e.message}")
        }
        return Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT)
    }


    fun convertUriToBitmap(uri: Uri) {
        getApplication<Application>().contentResolver.openInputStream(uri).apply {

            _bitmap.value = BitmapFactory.decodeStream(this)
        }
    }

    fun restoreState(inState: Bundle) {
        inState.getString(ACTUAL_BITMAP).apply {
            this?.let {
                _bitmap.value = stringToBitmap(it)
            }

        }
    }


    private fun stringToBitmap(charSequence: String): Bitmap {
        val byteCode = Base64.decode(charSequence, Base64.DEFAULT)
        //BitmapFactory.decodeByteArray(byteCode,0,byteCode.size)

        val inputStream = ByteArrayInputStream(byteCode)
        return BitmapFactory.decodeStream(inputStream)


    }

    override fun onCleared() {
        // super.onCleared()
        uiScope.cancel()
    }

    fun insert() {
        _onInsert.value=true
    }
}