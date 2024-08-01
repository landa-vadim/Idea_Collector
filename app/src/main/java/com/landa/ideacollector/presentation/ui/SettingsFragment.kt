package com.landa.ideacollector.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.preference.CheckBoxPreference
import androidx.preference.PreferenceFragmentCompat
import com.landa.ideacollector.R
import com.landa.ideacollector.presentation.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class SettingsFragment : PreferenceFragmentCompat() {

    private val settingsViewModel by activityViewModel<SettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            settingsViewModel.sortedTypeStateFlow.collect { sortedType ->
                preferenceScreen.getPreference(3).summary = sortedType.toString()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            settingsViewModel.themeStateFlow.collect { theme ->
                preferenceScreen.getPreference(4).summary = theme.toString()
            }
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        val checkBoxPreference = preferenceScreen.getPreference(1) as CheckBoxPreference
        preferenceScreen.getPreference(1).setOnPreferenceClickListener {
            val checkBoxState = checkBoxPreference.isChecked
            viewLifecycleOwner.lifecycleScope.launch {
                settingsViewModel.userSwitchedPasswordCheckBox(checkBoxState)
            }
            true
        }
        preferenceScreen.getPreference(2).setOnPreferenceClickListener {
            PasswordSetDialog().show(parentFragmentManager, "password_set_dialog")
            true
        }
        preferenceScreen.getPreference(3).setOnPreferenceClickListener {
            settingsViewModel.viewModelScope.launch {
                settingsViewModel.userChangedSortType()
            }
            true
        }
        preferenceScreen.getPreference(4).setOnPreferenceClickListener {
            settingsViewModel.viewModelScope.launch {
                settingsViewModel.userChangedTheme()
            }
            true
        }
        return
    }
}