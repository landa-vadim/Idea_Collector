package com.landa.ideacollector

import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.landa.ideacollector.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    var password: String? = null
    var passwordEnable = false
    var sortType: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val checkBox = binding.cbPasswordEnable
        checkBox.setOnClickListener {
            selectedCheckBox()
        }

        binding.bExit.setOnClickListener {
            goMainActivity()
        }

    }

    fun selectedCheckBox() {
        if (password == null) {
            setNewPassword()
            enablePassword()
        } else enablePassword()
    }

    fun enablePassword() {

    }

    fun setNewPassword() {

    }

    fun changeSortType() {

    }

    fun changeTheme() {

    }

    fun goMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}