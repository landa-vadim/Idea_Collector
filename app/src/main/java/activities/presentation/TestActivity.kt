package activities.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.landa.ideacollector.R
import com.landa.ideacollector.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {

    val viewModel: TestViewModel by viewModels()
    lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val position = savedInstanceState?.getInt("list_position") ?: 0
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun initViews() {

        binding = ActivityTestBinding.inflate(layoutInflater)

        binding.testButton.setOnClickListener {
            viewModel.userClickedButton("some Text")
        }

        viewModel.uiStateFlow.collect {
            when(it) {
                is DataLoaded -> { binding.listView.adapter.setData(it.list) }
                is Error -> { }
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
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("list_position", 0)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

// standart
// MainActivity -> SettingsActivity -> MainActivity -> SettingsActivity

// singleInstance
// MainActivity -> SettingsActivity ->

// presentation / UI layer
// Activity / Fragment  - то что видит пользователь
// ViewModel

// domain
// сущности, интерфейсы, бизнес-логика

// data
// имплементации интерфейсов репозиториев, работа с андроид специфичными вещами, например, БД