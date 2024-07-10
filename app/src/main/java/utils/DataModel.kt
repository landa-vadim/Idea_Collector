package utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.Password
import data.Priority

class DataModel : ViewModel() {

    val setPriority: MutableLiveData<Priority> by lazy {
        MutableLiveData<Priority>()
    }
    val setNewPassword: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val sortTypeChange: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val sortTypeChoice: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
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
    val themeChange: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val themeChoice: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val changeIdeasPriority: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

}