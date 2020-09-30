package com.example.saveimagelocaly.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.saveimagelocaly.R
import com.example.saveimagelocaly.data.Image
import kotlinx.android.synthetic.main.fragment_insert_image.view.*
import kotlinx.android.synthetic.main.item_image.view.*
import java.util.*

class ListImageAdapter(val imageList:List<Image>):
    RecyclerView.Adapter<ListImageAdapter.ItemImageViewHolder>() {

    class ItemImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Image) {
            itemView.tv_item_description.text=item.description
            val bitmap= getBitmapFromString(item.picture)

            itemView.item_image.setImageBitmap(bitmap)
        }

        private fun getBitmapFromString(picture: String): Bitmap {
            val byteCode= Base64.decode(picture,Base64.DEFAULT)


            return BitmapFactory.decodeByteArray(byteCode,0,byteCode.size)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemImageViewHolder {
      val layoutInflater=LayoutInflater.from(parent.context)
        val view=layoutInflater.inflate(R.layout.item_image,parent,false)
        return ItemImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemImageViewHolder, position: Int) {
        val item=imageList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
       return imageList.size
    }
}