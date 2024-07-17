package com.landa.ideacollector.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.preference.CheckBoxPreference
import androidx.preference.PreferenceFragmentCompat
import com.landa.ideacollector.R
import com.landa.ideacollector.presentation.viewmodel.IdeasViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel by viewModel<IdeasViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sortedTypeFlow.collect { sortedType ->
                preferenceScreen.getPreference(3).summary = sortedType.toString()
            }
        }
    }

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
            true
        }
        preferenceScreen.getPreference(4).setOnPreferenceClickListener {
            viewModel.viewModelScope.launch {
                viewModel.changeTheme()
            }
            viewModel.viewModelScope.launch {
                viewModel.themeFlow.collect { theme ->
                    it.summary = theme.toString()
                }
            }
            true
        }
        return
    }
}

