package activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import data.Idea
import adaptors.IdeaAdapter
import data.Priority
import com.landa.ideacollector.R
import com.landa.ideacollector.databinding.ActivityMainBinding
import data.dataBase.MainDb
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = IdeaAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = MainDb.getDb(this)
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
                    db.getDao().insertItem(idea)
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

    fun onLongClickGoSettingsActivity() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    fun changePriorityColor() {
        if (colorIndex > 1) colorIndex = -1
        binding.priorityImageButton.setBackgroundColor(getColor(colorList[++colorIndex]))
    }
}