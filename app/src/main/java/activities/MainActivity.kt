package activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import data.Idea
import adaptors.IdeaAdapter
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.preference.PreferenceManager
import data.Priority
import com.landa.ideacollector.R
import com.landa.ideacollector.databinding.ActivityMainBinding
import data.SortType
import data.SortTypeEnum
import data.dataBase.MainDb
import utils.DataModel
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = IdeaAdapter()
    private val dataModel: DataModel by viewModels()
    private lateinit var db: MainDb
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
//        val db = MainDb.getDb(this)
        db = MainDb.DatabaseManager.getDb(this)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
        val initialCheckBoxState = sharedPreferences.getBoolean("enablePassword", false)
        lockCheckBox(initialCheckBoxState)
        init(db)
        dataModel.sendEnteredPassword.observe(this, { passwordCheck(db, it) })
        dataModel.sortTypeChange.observe(this, {
            if (it) {
                Thread {
                    val oldIdeaList = db.getDao().getAllItems()
                    runOnUiThread { adapter.setData(oldIdeaList, sortTypeApply(db)) }
                }.start()
            }
        })

    }

    var colorIndex = 0
    val colorList = listOf(R.color.red, R.color.yellow, R.color.green)

    private fun init(db: MainDb) {
        binding.apply {
            ideasList.layoutManager = LinearLayoutManager(this@MainActivity)
            ideasList.adapter = adapter
            Thread {
                val oldIdeaList = db.getDao().getAllItems()
                runOnUiThread { adapter.setData(oldIdeaList, sortTypeApply(db)) }
//                runOnUiThread { adapter.setData(oldIdeaList) }
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
                    runOnUiThread { adapter.setData(oldIdeaList, sortTypeApply(db)) }
//                    runOnUiThread { adapter.setData(oldIdeaList) }
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
            lockImageView.setOnClickListener {
                openDialog()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
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

    fun passwordCheck(db: MainDb, enteredPassword: String) {
        Thread {
            if (db.getDaoPass().getPassword().lastOrNull()?.password == enteredPassword) {
                runOnUiThread {
                    lockCheckBox(false)
                    dataModel.passwordIsTrue.value = true
                }
            } else {
                runOnUiThread {
                    dataModel.passwordIsTrue.value = false
                }
            }
        }.start()
    }

    fun sortTypeApply(db: MainDb): SortTypeEnum {
        val getDao = db.getDaoSort()
        var sortTypeEnum = SortTypeEnum.DATE
        Thread {
            if (getDao.getSortType().isNotEmpty()) {
                val sortTypeEnumFromDb = db.getDaoSort().getSortType()[0].sortType
                if (sortTypeEnumFromDb == SortTypeEnum.DATE) {
                    sortTypeEnum = SortTypeEnum.PRIORITY
                    val newSortType = SortType(0, sortTypeEnum)
                    getDao.insertSortType(newSortType)
                } else {
                    sortTypeEnum = SortTypeEnum.DATE
                    val newSortType = SortType(0, sortTypeEnum)
                    getDao.insertSortType(newSortType)
                }
            } else {
                val sortType = SortType(0, sortTypeEnum)
                getDao.insertSortType(sortType)
            }
        }.start()
        return sortTypeEnum
    }

}