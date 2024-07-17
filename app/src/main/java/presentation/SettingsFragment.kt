package presentation

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import androidx.preference.CheckBoxPreference
import androidx.preference.PreferenceFragmentCompat
import com.landa.ideacollector.R
import domain.utilityClasses.IdeasViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
            viewModel.viewModelScope.launch {
                viewModel.changeSortType()
            }
            viewModel.viewModelScope.launch {
                viewModel.getSortedType().collect { sortedType ->
                    it.summary = sortedType.toString()
                }
            }
            true
        }
        preferenceScreen.getPreference(4).setOnPreferenceClickListener {
            viewModel.viewModelScope.launch {
                viewModel.changeTheme()
            }
            viewModel.viewModelScope.launch {
                viewModel.getTheme().collect { theme ->
                    it.summary = theme.toString()
                }
            }
            true
        }
        return
    }
}

