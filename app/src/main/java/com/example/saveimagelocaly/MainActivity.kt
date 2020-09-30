package com.example.saveimagelocaly

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Base64

import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.saveimagelocaly.data.Image
import com.example.saveimagelocaly.database.getSingleDatabase

import com.example.saveimagelocaly.viewmodels.InsertImageViewModel
import com.example.saveimagelocaly.viewmodels.InsertImageViewModelFactory
import kotlinx.android.synthetic.main.fragment_insert_image.*
import java.io.ByteArrayOutputStream

const val REQUEST_IMAGE_GET=1
class MainActivity : AppCompatActivity() {

   lateinit  var bitmap: Bitmap
   private lateinit var insertImageViewModel: InsertImageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val application= requireNotNull(this.application)
        val imageDao= getSingleDatabase(application.applicationContext).imageDao
       val imageViewModelFactory=InsertImageViewModelFactory(application, imageDao)
          picture.setImageDrawable(getDrawable(R.drawable.ic_launcher_background))
        insertImageViewModel =ViewModelProvider(this,imageViewModelFactory)[InsertImageViewModel::class.java]

        if (insertImageViewModel.isNewlyCreated &&savedInstanceState != null){
           insertImageViewModel.restoreState(savedInstanceState)
        }
         insertImageViewModel.isNewlyCreated=false

         insertImageViewModel.bitmap.observe(this, Observer {
               it?.let {
                   bitmap=it
                   picture.setImageBitmap(it)
               }
         })
        insertImageViewModel.onInsert.observe(this, Observer {
            if (it){
                if (this::bitmap.isInitialized){
                    val image=Image( description = edit_description.text.toString(),picture = insertImageViewModel.bitmapToString(bitmap))
                    insertImageViewModel.insetImage(image)
                    Toast.makeText(this, "inserted", Toast.LENGTH_SHORT).show()
                    insertImageViewModel.doneInserting()
                    navigateToListImageActivity(this)
                }

            }
        })

        val imageButton=findViewById<ImageButton>(R.id.edit_image)
        imageButton.setOnClickListener {
            Intent().apply {
                action=Intent.ACTION_GET_CONTENT
                type="image/*"

            }.also {
                if (it.resolveActivity(packageManager)!=null)
                    startActivityForResult(it, REQUEST_IMAGE_GET)
            }
        }

        save_picture.setOnClickListener {
            if (edit_description.text.toString().isNotEmpty())
                insertImageViewModel.insert()
            //Toast.makeText(this, "inserted", Toast.LENGTH_SHORT).show()

            else
            { edit_description.error="required"}





        }
    }

    private fun navigateToListImageActivity(mainActivity: MainActivity) {

        Intent(mainActivity,ListImagesActivity::class.java).apply {
            startActivity(this)
        }
    }

    /* private fun bitmapToString(bitmap: Bitmap): String {

         var bos=ByteArrayOutputStream()
         bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos)
         return Base64.encodeToString(bos.toByteArray(),Base64.DEFAULT)
     }*/

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(outState!=null)
        insertImageViewModel.storeState(outState)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== REQUEST_IMAGE_GET && resultCode== Activity.RESULT_OK){
            if (data != null) {
                data.data?.let { insertImageViewModel.convertUriToBitmap(it) }
            }
        }
    }
}