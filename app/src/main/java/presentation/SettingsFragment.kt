package presentation

import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.PreferenceFragmentCompat
import com.landa.ideacollector.R
import domain.utilityClasses.IdeasViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel by viewModel<IdeasViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        val checkBoxPreference = preferenceScreen.getPreference(1) as CheckBoxPreference
        preferenceScreen.getPreference(1).setOnPreferenceClickListener {
            if (checkBoxPreference.isChecked) {
                viewModel.userSwitchedPassCheckBox(true)
            } else {
                viewModel.userSwitchedPassCheckBox(false)
            }
            true
        }
        preferenceScreen.getPreference(2).setOnPreferenceClickListener {
            true
        }
        preferenceScreen.getPreference(3).setOnPreferenceClickListener {
            true
        }
        preferenceScreen.getPreference(4).setOnPreferenceClickListener {
            true
        }
        return
    }
}

