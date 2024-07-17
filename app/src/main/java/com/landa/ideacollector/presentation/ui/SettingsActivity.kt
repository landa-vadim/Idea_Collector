package com.landa.ideacollector.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.landa.ideacollector.domain.model.Password

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openFragment(SettingsFragment())
    }
    private fun openFragment(fragment: PreferenceFragmentCompat) {
        supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, fragment)
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    private fun openDialog() {
        PasswordSetDialog().show(supportFragmentManager, "password_set_dialog")
    }
    private fun setPassword(pass: Password) {

    }
}