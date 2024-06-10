package activities

import android.app.Dialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.landa.ideacollector.R
import utils.DataModel

class SettingsActivity : AppCompatActivity() {
    private val dataModel: DataModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openFragment(SettingsFragment())
        dataModel.setNewPassword.observe(this, { if (it) openDialog() })
//        dataModel.setPasswordForAccess.observe(this, { if (it)  })
//        dataModel.sortTypeChange.observe(this, { if (it)  })
//        dataModel.themeChange.observe(this, { if (it)  })
    }


    private fun openFragment(fragment: PreferenceFragmentCompat) {
        supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, fragment)
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun openDialog() {
        val dialogBinding = layoutInflater.inflate(R.layout.password_set_dialog, null)
        val passwordSetDialog = Dialog(this)
        passwordSetDialog.setContentView(dialogBinding)
        passwordSetDialog.show()
        passwordSetDialog.setCancelable(false)
    }


}

//class SettingsActivity : AppCompatActivity() {

//    private lateinit var binding: ActivitySettingsBinding
//    var password: String? = null
//    var passwordEnable = false
//    var sortType: Int? = null

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        binding = ActivitySettingsBinding.inflate(layoutInflater)
//        setContentView(binding.root)

//        val checkBox = binding.passwordEnableCheckBox
//        checkBox.setOnClickListener {
//            selectedCheckBox()
//        }
//
//        binding.exitButton.setOnClickListener {
//            goMainActivity()
//        }
//
//    }
//
//    fun selectedCheckBox() {
//        if (password == null) {
//            setNewPassword()
//            enablePassword()
//        } else enablePassword()
//    }
//
//    fun enablePassword() {
//
//    }
//
//    fun setNewPassword() {
//
//    }
//
//    fun changeSortType() {
//
//    }
//
//    fun changeTheme() {
//
//    }
//
//    fun goMainActivity() {
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
//    }
//
//}