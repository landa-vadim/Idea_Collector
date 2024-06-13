package utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.Password

class DataModel : ViewModel() {

    val setNewPassword: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val sortTypeChange: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val newPassword: MutableLiveData<Password> by lazy {
        MutableLiveData<Password>()
    }
    val passwordIsTrue: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val sendEnteredPassword: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

}