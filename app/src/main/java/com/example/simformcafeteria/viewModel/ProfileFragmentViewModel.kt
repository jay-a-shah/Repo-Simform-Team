package com.example.simformcafeteria.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileFragmentViewModel : ViewModel() {
    var emailDialog: MutableLiveData<String> = MutableLiveData()
    var nameDialog: MutableLiveData<String> = MutableLiveData()
    var empTypeTrainneDialog: MutableLiveData<Boolean> = MutableLiveData()
    var empTypEmpDialog: MutableLiveData<Boolean> = MutableLiveData()
    var empCodeDialog: MutableLiveData<String> = MutableLiveData()

}