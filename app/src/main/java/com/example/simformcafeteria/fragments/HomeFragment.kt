package com.example.simformcafeteria.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.simformcafeteria.R
import com.example.simformcafeteria.Utils.DATEFORMATE
import com.example.simformcafeteria.adapters.ItemAdapter
import com.example.simformcafeteria.model.ItemDataClass
import com.example.simformcafeteria.Utils.ZERO
import com.example.simformcafeteria.databinding.FragmentHomeBinding
import com.example.simformcafeteria.viewModel.HomeFragmentViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val homeFragmentViewModel: HomeFragmentViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val view = binding.root
       val  currentDate = SimpleDateFormat(DATEFORMATE, Locale.getDefault()).format(Date())
        val list = ArrayList<ItemDataClass>()
        list.apply {
            add(ItemDataClass(getString(R.string.morning), currentDate, ZERO))
            add(ItemDataClass(getString(R.string.lunch), currentDate, ZERO))
            add(ItemDataClass(getString(R.string.evening), currentDate, ZERO))
        }
        binding.recyclerHome.adapter = ItemAdapter(list, requireContext())
        return view
    }
    // Login Screen Change
    //Sign Up Screen Added
}