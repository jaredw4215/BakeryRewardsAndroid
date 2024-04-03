package com.example.bakeryrewardsandroid

import android.app.Dialog
import android.content.Intent
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
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import java.net.URL
import java.util.concurrent.Executors
import kotlin.random.Random

class HomeItemAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<ItemData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return itemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false))
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
    fun sumbitList(itemList: List<ItemData>){
        items = itemList
        notifyDataSetChanged()
    }
    inner class itemViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        val itemIamge = itemView.findViewById<ImageView>(R.id.itemImageView)
        val itemName = itemView.findViewById<TextView>(R.id.itemName)
        val itemCost = itemView.findViewById<TextView>(R.id.itemCost)
        val itemBuyButton = itemView.findViewById<Button>(R.id.itembuyButton)
        private var auth : FirebaseAuth = FirebaseAuth.getInstance()
        private var user : FirebaseUser = auth.currentUser!!
        private var database: FirebaseDatabase = Firebase.database

        fun bind(itemData: ItemData){
            itemName.text = itemData.name
            itemCost.text = itemData.cost
            loadImage(itemData.path!!,itemIamge)
            itemBuyButton.setOnClickListener {
                loadDialog(itemData.cost!!)
            }
        }
        private fun loadDialog(cost: String){
            val p = Dialog(itemView.context)
            p.window?.setBackgroundDrawableResource(android.R.color.transparent)
            p.setContentView(R.layout.buy_card_view)
            p.setCancelable(true)
            val cancel = p.findViewById<Button>(R.id.cancelButton)
            val confirm = p.findViewById<Button>(R.id.confirmButton)
            val balance = p.findViewById<TextView>(R.id.costTV)
            cancel.setOnClickListener {
                p.dismiss()
            }
            confirm.setOnClickListener {
                val reference = database.getReference("users")
                reference.child(user.uid).child("points").get().addOnSuccessListener {
                    Log.i("firebase", "Got value ${it.value}")
                    var points = it.value.toString()
                    if (points.toInt() >= cost.toInt()){
                        reference.child(user.uid).child("points").setValue(points.toInt()-cost.toInt())
                        p.dismiss()
                        Toast.makeText(itemView.context,"PURCHASE CONFIRMED!",Toast.LENGTH_SHORT).show()
                        displayCode()
                        //DISPLAY REDEEMABLE CODE
                        //SEND CONFIRMATION EMAIL
                    }
                    else{
                        Log.e("firebase","Not enough points")
                        Toast.makeText(itemView.context,"NOT ENOUGH POINTS",Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener{
                    Log.e("firebase", "Error getting data", it)
                }
            }
            balance.text = "$cost Points"
            p.show()
        }

        private fun displayCode(){
            val code = (100000..999999).random().toString()
            val reference = database.getReference("users")
            reference.child(user.uid).child("code").setValue(code)
            val p = Dialog(itemView.context)
            p.window?.setBackgroundDrawableResource(android.R.color.transparent)
            p.setContentView(R.layout.code_view)
            p.setCancelable(true)
            val codeTV = p.findViewById<TextView>(R.id.codeTV)
            val emailTV = p.findViewById<TextView>(R.id.emailTV)
            codeTV.text = code
            emailTV.text = user.email
            p.show()
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