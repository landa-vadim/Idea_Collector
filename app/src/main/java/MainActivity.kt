import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.preference.PreferenceManager
import com.landa.ideacollector.R
import com.landa.ideacollector.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = IdeaAdapter()
    private val dataModel: IdeasViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == "enablePassword") {
                val checkBoxState = sharedPreferences.getBoolean(key, false)
                lockCheckBox(checkBoxState)
            }
        }
    private val recyclerViewScope: CoroutineScope? =
        binding.ideasList.findViewTreeLifecycleOwner()?.lifecycleScope


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ideasList.adapter = adapter
        onLongClickGoSettingsActivity()
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
        val initialCheckBoxState = sharedPreferences.getBoolean("enablePassword", false)
        lockCheckBox(initialCheckBoxState)
        recyclerViewScope?.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                dataModel.getSortedIdeas().collect {
                    adapter.setData(it)
                }
            }
        }
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