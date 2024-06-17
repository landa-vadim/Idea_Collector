package presentation

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.preference.PreferenceFragmentCompat
import com.landa.ideacollector.R
import domain.utilityClasses.DataModel

class SettingsFragment : PreferenceFragmentCompat() {

    private val dataModel: DataModel by activityViewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        preferenceScreen.getPreference(2).setOnPreferenceClickListener {
            it.setSummary("Password is SET!")
            dataModel.setNewPassword.value = true
            true
        }
        preferenceScreen.getPreference(3).setOnPreferenceClickListener {
            dataModel.sortTypeChange.value = true
            dataModel.sortTypeChoice.observe(this, { sortType ->
                it.setSummary("$sortType")
            })
            true
        }
        preferenceScreen.getPreference(4).setOnPreferenceClickListener {
            it.setSummary("Dark")
            true
        }
        return
    }
}

