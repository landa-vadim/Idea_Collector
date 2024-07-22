package com.landa.ideacollector.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.landa.ideacollector.R
import com.landa.ideacollector.databinding.ActivityMainBinding
import com.landa.ideacollector.domain.model.ThemeEnum
import com.landa.ideacollector.presentation.adapter.IdeaAdapter
import com.landa.ideacollector.presentation.viewmodel.MainViewModel
import com.landa.ideacollector.presentation.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = IdeaAdapter()
    private val mainViewModel by viewModel<MainViewModel>()
    private val settingsViewModel by viewModel<SettingsViewModel>()

    private var colorIndex = 0
    private val colorList = listOf(R.color.red, R.color.yellow, R.color.green)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.getSortedIdeas().collect {
                    adapter.setData(it)
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                settingsViewModel.themeFlow.collect { state ->
                themeSetResources(state)

                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                settingsViewModel.passCheckBoxStateFlow.collect { state ->
                    lockIdeas(state)
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                settingsViewModel.passLockStateFlow.collect { state ->
                    if (settingsViewModel.passCheckBoxStateFlow.value) lockIdeas(state)
                }
            }
        }
        binding.apply {
            ideasList.layoutManager = LinearLayoutManager(this@MainActivity)
            ideasList.adapter = adapter
            doneImageButton.setOnClickListener {
                val ideaText = ideaEditText.text.toString()
                ideaEditText.text.clear()
                mainViewModel.userClickedDoneButton(ideaText, colorIndex)
            }
            priorityImageButton.setOnClickListener {
                if (colorIndex > 1) colorIndex = -1
                binding.priorityImageButton.setBackgroundColor(getColor(colorList[++colorIndex]))
            }
            doneImageButton.setOnLongClickListener {
                val etIdeaText = ideaEditText.text
                if (etIdeaText.isEmpty()) {
                    val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                    startActivity(intent)
                    return@setOnLongClickListener true
                } else return@setOnLongClickListener false
            }
            lockImageView.setOnClickListener {
                PasswordAskDialog().show(supportFragmentManager, "password_ask_dialog")
            }
        }
    }

    override fun onPause() {
        super.onPause()
        lifecycleScope.launch {
            settingsViewModel.renewIdeasLockState()
        }
    }

    private fun lockIdeas(lock: Boolean) {
        val visibility =
            when (lock) {
                true -> View.VISIBLE
                false -> View.INVISIBLE
            }
        binding.lockImageView.visibility = visibility
        binding.bg1ImageView.visibility = visibility
        binding.bg2ImageView.visibility = visibility
        binding.bg3ImageView.visibility = visibility
    }
    private fun themeSetResources(theme: ThemeEnum) {
        when (theme) {
            ThemeEnum.LIGHT -> binding.doneImageButton.setImageResource(R.drawable.ic_done)
            ThemeEnum.DARK -> binding.doneImageButton.setImageResource(R.drawable.ic_done_dark)
        }
    }
}