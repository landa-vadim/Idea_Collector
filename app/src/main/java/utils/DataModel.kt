package utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataModel: ViewModel() {

    val setNewPassword: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val setPasswordForAccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val sortTypeChange: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val themeChange: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

}