package presentation

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.landa.ideacollector.R
import domain.utilityClasses.IdeasViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel by viewModel<IdeasViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        preferenceScreen.getPreference(2).setOnPreferenceChangeListener { checkBox, _ ->
            if (checkBox.isEnabled) {
                viewModel.userSwitchedPassCheckBox(true)
            } else {
                viewModel.userSwitchedPassCheckBox(false)
            }
            true
        }
//        {
//
//            it.setSummary("Password is SET!")
//            true
//        }
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

