package com.example.simformcafeteria.Fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.simformcafeteria.R
import com.example.simformcafeteria.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(){
  lateinit var binding: FragmentProfileBinding
  lateinit var dialog: Dialog

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}