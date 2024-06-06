package com.landa.ideacollector

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.landa.ideacollector.databinding.ActivityMainBinding
import java.util.Date
import kotlin.concurrent.thread

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
        }
    }

    fun changePriorityColor() {
        if (colorIndex > 1) colorIndex = -1
        binding.ibPriority.setBackgroundColor(getColor(colorList[++colorIndex]))
    }
}