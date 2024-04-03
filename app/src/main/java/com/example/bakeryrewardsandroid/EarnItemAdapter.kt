package com.example.bakeryrewardsandroid

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception
import java.net.URL
import java.util.concurrent.Executors

class EarnItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var items: List<ItemData> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return itemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.earn_item_view,parent,false))
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is itemViewHolder -> {
                holder.bind(items[position])
            }
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }
    fun submitList(itemList: List<ItemData>){
        items = itemList
    }
    fun setFilteredList(filteredList: List<ItemData>){
        items = filteredList
        notifyDataSetChanged()
    }
    class itemViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        val itemIamge = itemView.findViewById<ImageView>(R.id.earnItemIamge)
        val itemName = itemView.findViewById<TextView>(R.id.earnItemName)
        val itemValue = itemView.findViewById<TextView>(R.id.earnItemPoints)


        fun bind(itemData: ItemData){
            itemName.text = itemData.name
            itemValue.text = itemData.cost
            loadImage(itemData.path!!,itemIamge)
        }
        private fun loadImage(path: String,imageView: ImageView){
            val executor = Executors.newSingleThreadExecutor()
            val handler = android.os.Handler(Looper.getMainLooper())
            var image: Bitmap?

            executor.execute {
                try {
                    val n = URL(path).openStream()
                    image = BitmapFactory.decodeStream(n)
                    handler.post{
                        imageView.setImageBitmap(image)
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}