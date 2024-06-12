package activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import data.Password
import data.dataBase.MainDb
import utils.DataModel

class SettingsActivity : AppCompatActivity() {
    private val dataModel: DataModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openFragment(SettingsFragment())
        val db = MainDb.getDb(this)
        dataModel.setNewPassword.observe(this, { if (it) openDialog() })
        dataModel.newPassword.observe(this, { setPassword(db, it) })
//      dataModel.sortTypeChange.observe(this, { if (it)  })
//      dataModel.themeChange.observe(this, { if (it)  })
    }


    private fun openFragment(fragment: PreferenceFragmentCompat) {
        supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, fragment)
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun openDialog() {
        PasswordSetDialog().show(supportFragmentManager, "password_dialog")
    }

    private fun setPassword(db: MainDb, pass: Password) {
        Thread {
            db.getDaoPass().insertPass(pass)
        }.start()
    }

}