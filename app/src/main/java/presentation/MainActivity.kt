package presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import data.IdeaAdapter
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.landa.ideacollector.R
import com.landa.ideacollector.databinding.ActivityMainBinding
import domain.utilityClasses.IdeasViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = IdeaAdapter()
    private val viewModel by viewModel<IdeasViewModel>()

//    private lateinit var sharedPreferences: SharedPreferences
//    private val preferenceChangeListener =
//        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
//            if (key == "enablePassword") {
//                val checkBoxState = sharedPreferences.getBoolean(key, false)
//                lockCheckBox(checkBoxState)
//            }
//        }
    private var colorIndex = 0
    private val colorList = listOf(R.color.red, R.color.yellow, R.color.green)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSortedIdeas().collect {
                    adapter.setData(it)
                }
            }
        }
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
//        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
//        val initialCheckBoxState = sharedPreferences.getBoolean("enablePassword", false)
//        lockCheckBox(initialCheckBoxState)

        binding.apply {
            ideasList.layoutManager = LinearLayoutManager(this@MainActivity)
            ideasList.adapter = adapter
            doneImageButton.setOnClickListener {
                val ideaText = ideaEditText.text.toString()
                ideaEditText.text.clear()
                viewModel.userClickedDoneButton(ideaText, colorIndex)
            }
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
                openDialog()
            }
        }
    }

    private fun lockCheckBox(isChecked: Boolean) {
        var visability = View.INVISIBLE
        if (isChecked) visability = View.VISIBLE
        binding.lockImageView.visibility = visability
        binding.bg1ImageView.visibility = visability
        binding.bg2ImageView.visibility = visability
        binding.bg3ImageView.visibility = visability
    }

    fun onLongClickGoSettingsActivity() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    fun changePriorityColor() {
        if (colorIndex > 1) colorIndex = -1
        binding.priorityImageButton.setBackgroundColor(getColor(colorList[++colorIndex]))
    }

    fun openDialog() {
        PasswordAskDialog().show(supportFragmentManager, "password_ask_dialog")
    }


}