package presentation

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceFragmentCompat
import com.landa.ideacollector.R
import domain.utilityClasses.ShrdPref

class SettingsFragment : PreferenceFragmentCompat() {

    private val dataModel: ViewModel by activityViewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        preferenceScreen.getPreference(2).setOnPreferenceClickListener {

            it.setSummary("Password is SET!")
            true
        }
        preferenceScreen.getPreference(3).setOnPreferenceClickListener {
            true
        }
        preferenceScreen.getPreference(4).setOnPreferenceClickListener {
            it.setSummary("Dark")
            true
        }
        return
    }
}

