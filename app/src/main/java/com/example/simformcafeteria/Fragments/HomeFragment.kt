package com.example.simformcafeteria.Fragments

import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simformcafeteria.R
import com.example.simformcafeteria.adapter.ItemAdapter
import com.example.simformcafeteria.adapter.ItemDataClass
import java.text.DateFormat

class HomeFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val currentDate: String
        val calendar: Calendar = Calendar.getInstance()
        currentDate = DateFormat.getDateInstance().format(calendar.time)
        var recyclerView = view.findViewById<RecyclerView>(R.id.recyclerHome)
        val list = ArrayList<ItemDataClass>()

        list.apply {
            add(ItemDataClass(getString(R.string.morning),currentDate,true,R.drawable.like,R.drawable.dislike))
            add(ItemDataClass(getString(R.string.lunch),currentDate,false,R.drawable.like,R.drawable.dislike))
            add(ItemDataClass(getString(R.string.evening),currentDate,false,R.drawable.like,R.drawable.dislike))
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = ItemAdapter(list, requireContext())
        return view
    }
}