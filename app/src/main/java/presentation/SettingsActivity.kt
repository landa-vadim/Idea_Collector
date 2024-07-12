package presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceFragmentCompat
import domain.dataClasses.Password
import domain.dataBase.MainDb

class SettingsActivity : AppCompatActivity() {
    private val dataModel: ViewModel by viewModels()
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