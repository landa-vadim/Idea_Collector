package activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import adaptors.IdeaAdapter
import android.content.SharedPreferences
import android.view.View
import androidx.activity.viewModels
import androidx.preference.PreferenceManager
import com.landa.ideacollector.R
import com.landa.ideacollector.databinding.ActivityMainBinding
import data.Priority
import data.SortTypeEnum
import utils.DataModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = IdeaAdapter()
    private val dataModel: DataModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == "enablePassword") {
                val checkBoxState = sharedPreferences.getBoolean(key, false)
                lockCheckBox(checkBoxState)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ideasList.adapter = adapter
        onLongClickGoSettingsActivity()
        dataModel.passwordIsTrue.observe(this) { lockCheckBox(it) }
        dataModel.setPriority.observe(this) { changePriorityColor(it) }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
        val initialCheckBoxState = sharedPreferences.getBoolean("enablePassword", false)
        lockCheckBox(initialCheckBoxState)
    }

    override fun onStart() {
        super.onStart()
        with(binding) {
            doneImageButton.setOnClickListener {
                val ideaText = ideaEditText.text.toString()

                ideaEditText.text.clear()
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun lockCheckBox(isChecked: Boolean) {
        var visability = View.INVISIBLE
        if (isChecked) visability = View.VISIBLE
        binding.lockImageView.visibility = visability
        binding.bg1ImageView.visibility = visability
        binding.bg2ImageView.visibility = visability
        binding.bg3ImageView.visibility = visability
    }

    private fun onLongClickGoSettingsActivity() {
        if (binding.ideaEditText.text.isEmpty()) {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun changePriorityColor(priority: Priority) {
        binding.priorityImageButton.setOnClickListener {
            val priorityColor =
                when (priority) {
                    Priority.HIGH -> R.color.red
                    Priority.MEDIUM -> R.color.yellow
                    Priority.LOW -> R.color.green
                }
            binding.priorityImageButton.setBackgroundColor(priorityColor)
        }
    }

    private fun openPasswordAskDialog() {
        PasswordAskDialog().show(supportFragmentManager, "password_ask_dialog")
    }


}


private fun init() {
    binding.apply {
        ideasList.layoutManager = LinearLayoutManager(this@MainActivity)
        ideasList.adapter = adapter
        adapter.setData(oldIdeaList, sortTypeApply(db))

        priorityImageButton.setOnClickListener {
            changePriorityColor()
        }
        doneImageButton.setOnLongClickListener {
            val etIdeaText = ideaEditText.text
            if (etIdeaText.isEmpty()) {
                onLongClickGoSettingsActivity()
                return@setOnLongClickListener true
            } else return@setOnLongClickListener false
        }
        lockImageView.setOnClickListener {
            openPasswordAskDialog()
        }
    }
}