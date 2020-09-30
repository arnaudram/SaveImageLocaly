package com.example.saveimagelocaly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.saveimagelocaly.adapter.ListImageAdapter
import com.example.saveimagelocaly.database.getSingleDatabase
import com.example.saveimagelocaly.viewmodels.ListImageViewModel
import com.example.saveimagelocaly.viewmodels.ListImageViewModelFactory
import kotlinx.android.synthetic.main.activity_list_images.*

class ListImagesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_images)
        val application= requireNotNull(this.application)
        val imageDao= getSingleDatabase(application.applicationContext).imageDao
        val listImageViewModelFactory=ListImageViewModelFactory(application,imageDao)
        val listImageViewModel by lazy { ViewModelProvider(this,listImageViewModelFactory)[ListImageViewModel::class.java] }

        listImageViewModel.images.observe(this, Observer {
          val adapter=ListImageAdapter(it)
            adapter.notifyDataSetChanged()
            recycle_item_image_view.setHasFixedSize(true)
            recycle_item_image_view.adapter=adapter
        })
    }
}