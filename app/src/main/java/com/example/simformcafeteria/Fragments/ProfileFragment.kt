package com.example.simformcafeteria.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.simformcafeteria.R
import com.example.simformcafeteria.databinding.BottomSheetDialogBinding
import com.example.simformcafeteria.databinding.FragmentProfileBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.bottom_sheet_dialog.view.btnClose
import kotlinx.android.synthetic.main.bottom_sheet_dialog.view.btnSubmit
import kotlinx.android.synthetic.main.bottom_sheet_dialog.view.editTextEmailDialog
import kotlinx.android.synthetic.main.bottom_sheet_dialog.view.editTextEmployeeCodeDialog
import kotlinx.android.synthetic.main.bottom_sheet_dialog.view.editTextEmployeeMaterialLayout
import kotlinx.android.synthetic.main.bottom_sheet_dialog.view.editTextNameDialog
import kotlinx.android.synthetic.main.bottom_sheet_dialog.view.radioGroup


class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnEditProfile.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
            val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
            bottomSheetView.apply {
                btnClose.setOnClickListener {
                    bottomSheetDialog.dismiss()
                }
                radioGroup.setOnCheckedChangeListener { _, p1 ->
                    bottomSheetView.editTextEmployeeMaterialLayout.visibility = if (p1 == R.id.radioButtonEmployee) { View.VISIBLE } else { View.GONE }
                }
                btnSubmit.setOnClickListener {
                    bottomSheetView.apply {
                        editTextEmailDialog.text?.clear()
                        editTextNameDialog.text?.clear()
                        editTextEmployeeCodeDialog.text?.clear()
                    }
                }
            }
            bottomSheetDialog.apply {
                setContentView(bottomSheetView)
                show()
                setCancelable(false)
            }
        }
    }
}
