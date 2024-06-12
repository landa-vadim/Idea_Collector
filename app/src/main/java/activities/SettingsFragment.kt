package activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.preference.CheckBoxPreference
import androidx.preference.PreferenceFragmentCompat
import com.landa.ideacollector.R
import utils.DataModel

class SettingsFragment : PreferenceFragmentCompat() {

    private val dataModel: DataModel by activityViewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        val checkBox = preferenceScreen.getPreference(1)
        if ((checkBox as CheckBoxPreference).isChecked) {
            Log.e("SettingsFragment", "checkBox.isChecked")
            dataModel.setPasswordForAccess.value = View.VISIBLE
        } else dataModel.setPasswordForAccess.value = View.INVISIBLE
        preferenceScreen.getPreference(2).setOnPreferenceClickListener {
            it.setSummary("Password is SET!")
            dataModel.setNewPassword.value = true
            true
        }
        preferenceScreen.getPreference(3).setOnPreferenceClickListener {
            it.setSummary("Date")
            true
        }
        preferenceScreen.getPreference(4).setOnPreferenceClickListener {
            it.setSummary("Dark")

            true
        }
        return
    }


}

