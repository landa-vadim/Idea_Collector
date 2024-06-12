package utils

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.Password

class DataModel : ViewModel() {

    val setPasswordForAccess: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val setNewPassword: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val sortTypeChange: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val themeChange: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val newPassword: MutableLiveData<Password> by lazy {
        MutableLiveData<Password>()
    }

}