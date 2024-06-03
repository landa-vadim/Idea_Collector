package com.landa.ideacollector

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
        init()
    }

    var colorIndex = 0
    val red = R.color.red
    val yellow = R.color.yellow
    val green = R.color.green
    val colorList = listOf(red, yellow, green)

    private fun init() {
        binding.apply {
            rvIdeas.layoutManager = LinearLayoutManager(this@MainActivity)
            rvIdeas.adapter = adapter
            ibDone.setOnClickListener {
                val ideasPriorityColor = colorList[colorIndex]
                val ideasText = etIdea.text.toString()
                val ideasDate = Date().toString()
                val idea = Idea(ideasPriorityColor, ideasText, ideasDate)
                adapter.addIdea(idea)
                etIdea.text.clear()
            }
            ibPriority.setOnClickListener {
                changePriorityColor()
            }
        }
    }

    fun changePriorityColor() {
        if (colorIndex > 2) colorIndex = 0
        binding.ibPriority.setBackgroundColor(getColor(colorList[colorIndex++]))

    }

}