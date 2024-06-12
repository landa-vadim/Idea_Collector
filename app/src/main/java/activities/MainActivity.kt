package activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import data.Idea
import adaptors.IdeaAdapter
import android.content.SharedPreferences
import android.view.View
import androidx.preference.PreferenceManager
import data.Priority
import com.landa.ideacollector.R
import com.landa.ideacollector.databinding.ActivityMainBinding
import data.dataBase.MainDb
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = IdeaAdapter()
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
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = MainDb.getDb(this)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
        val initialCheckBoxState = sharedPreferences.getBoolean("enablePassword", false)
        lockCheckBox(initialCheckBoxState)
        init(db)
    }

    var colorIndex = 0
    val colorList = listOf(R.color.red, R.color.yellow, R.color.green)

    private fun init(db: MainDb) {
        binding.apply {
            ideasList.layoutManager = LinearLayoutManager(this@MainActivity)
            ideasList.adapter = adapter
            Thread {
                val oldIdeaList = db.getDao().getAllItems()
                runOnUiThread { adapter.setData(oldIdeaList) }
            }.start()
            doneImageButton.setOnClickListener {
                val idea = Idea(
                    null,
                    Priority.entries[colorIndex],
                    ideaEditText.text.toString(),
                    Date().toString()
                )
                ideaEditText.text.clear()
                Thread {
                    db.getDao().insertIdea(idea)
                    val oldIdeaList = db.getDao().getAllItems()
                    runOnUiThread { adapter.setData(oldIdeaList) }
                }.start()
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
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    private fun lockCheckBox(isChecked: Boolean) {
        val checked = View.VISIBLE
        val notChecked = View.INVISIBLE
        if (isChecked) {
            binding.lockImageView.visibility = checked
            binding.bg1ImageView.visibility = checked
            binding.bg2ImageView.visibility = checked
            binding.bg3ImageView.visibility = checked
        } else {
            binding.lockImageView.visibility = notChecked
            binding.bg1ImageView.visibility = notChecked
            binding.bg2ImageView.visibility = notChecked
            binding.bg3ImageView.visibility = notChecked
        }
    }

    fun onLongClickGoSettingsActivity() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    fun changePriorityColor() {
        if (colorIndex > 1) colorIndex = -1
        binding.priorityImageButton.setBackgroundColor(getColor(colorList[++colorIndex]))
    }
}