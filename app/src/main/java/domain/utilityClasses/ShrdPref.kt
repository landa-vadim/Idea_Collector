package domain.utilityClasses

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow

class ShrdPref(val context: Context) {

    fun passCheckBoxSetValue(value: Boolean) {
        val passCheckBoxSharedPref: SharedPreferences =
            context.getSharedPreferences("enablePasswordCheckBox", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = passCheckBoxSharedPref.edit()
        editor.putBoolean("enablePassword", value)
        editor.apply()
    }

    fun passCheckBoxGetValue(): Boolean {
        val passCheckBoxSharedPref: SharedPreferences =
            context.getSharedPreferences("enablePasswordCheckBox", Context.MODE_PRIVATE)
        return passCheckBoxSharedPref.getBoolean("enablePassword", false)
    }

    fun sortTypeChoiceValue() {

    }

    fun themeChoiceValue() {

    }
}