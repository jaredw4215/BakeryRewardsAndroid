package com.example.bakeryrewardsandroid

import android.location.GnssAntennaInfo.Listener
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import java.util.Locale

class EarnFragment : Fragment() {
    lateinit var earnItemAdapter: EarnItemAdapter
    lateinit var earnRecyclerView: RecyclerView
    lateinit var itemList: ArrayList<ItemData>
    lateinit var searchView: androidx.appcompat.widget.SearchView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_earn, container, false)
        val path = "https://firebasestorage.googleapis.com/v0/b/bakeryrewardsandroid.appspot.com/o/Goodie.jpg?alt=media&token=9f7b86e0-31eb-4f70-9940-0705d82d822e"
        searchView = v.findViewById(R.id.earnSearchView)
        searchView.setOnQueryTextListener( object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
        itemList = arrayListOf(
            ItemData("Goodie_1","40",path),
            ItemData("Goodie_2","30",path),
            ItemData("Goodie_3","50",path),
            ItemData("Goodie_4","30",path),
            ItemData("Goodie_5","30",path),
            ItemData("Goodie_6","320",path),
            ItemData("Goodie_7","30",path),
            ItemData("Goodie_8","30",path),
            ItemData("Goodie_9","320",path),
            ItemData("Goodie_10","320",path)
        )
        earnItemAdapter = EarnItemAdapter()
        earnRecyclerView = v.findViewById(R.id.earnFragRecycler)
        earnRecyclerView.adapter = earnItemAdapter
        earnRecyclerView.layoutManager = GridLayoutManager(v.context,2)
        earnRecyclerView.setHasFixedSize(true)
        earnItemAdapter.submitList(itemList)
        return v
    }

    private fun filterList(query: String?){
        if (query != null){
            val filteredList = arrayListOf<ItemData>()
            for (i in itemList){
                if (i.name!!.lowercase().contains(query) || i.name.uppercase().contains(query)){
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()){
                Toast.makeText(context,"No Results",Toast.LENGTH_SHORT).show()
            }else{
                earnItemAdapter.setFilteredList(filteredList)
            }
        }
    }
}