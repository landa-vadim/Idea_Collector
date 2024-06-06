package com.landa.ideacollector

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.landa.ideacollector.databinding.ActivityMainBinding
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
            rvIdeas.layoutManager = LinearLayoutManager(this@MainActivity)
            rvIdeas.adapter = adapter
            Thread {
                db.getDao().getAllItems().forEach { idea ->
                    adapter.addIdea(idea)
                }
            }.start()
            ibDone.setOnClickListener {
                val idea = Idea(
                    null,
                    Priority.entries[colorIndex],
                    etIdea.text.toString(),
                    Date().toString()
                )
                etIdea.text.clear()
                Thread {
                    db.getDao().insertItem(idea)
                    val oldIdeaList = db.getDao().getAllItems()
                    runOnUiThread { adapter.setData(oldIdeaList) }
                }.start()
            }
            ibPriority.setOnClickListener {
                changePriorityColor()
            }
            ibDone.setOnLongClickListener {
                val etIdeaText = etIdea.text
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
        binding.ibPriority.setBackgroundColor(getColor(colorList[++colorIndex]))
    }
}