package activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import data.Idea
import adaptors.IdeaAdapter
import android.content.SharedPreferences
import android.content.res.Configuration
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
        dataModel.sendEnteredPassword.observe(this, { passwordCheck(db, it) })
        dataModel.sortTypeChange.observe(this, {
            Thread {
                val oldIdeaList = db.getDao().getAllItems()
                runOnUiThread { adapter.setData(oldIdeaList, sortTypeApply(db)) }
            }.start()
        })
//        dataModel.themeChange.observe(this, { themeChange(db) })
    }

    var colorIndex = 0
    val colorList = listOf(R.color.red, R.color.yellow, R.color.green)

    private fun init(db: MainDb) {
        binding.apply {
            ideasList.layoutManager = LinearLayoutManager(this@MainActivity)
            ideasList.adapter = adapter
            Thread {
                Log.e("VADIM", "init first Thread into")
                val oldIdeaList = db.getDao().getAllItems()
                Log.e("VADIM", "init first Thread get oldIdeaList")
                runOnUiThread {
                    Log.e("VADIM", "init first Thread runOnUiThread")
                    adapter.setData(oldIdeaList, sortTypeApply(db)) }
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
                openPasswordAskDialog()
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

    fun openPasswordAskDialog() {
        PasswordAskDialog().show(supportFragmentManager, "password_ask_dialog")
    }

    fun passwordCheck(db: MainDb, it: String) {
        Thread {
            if (db.getDaoPass().getPassword().lastOrNull()?.password == it) {
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
            Log.e("VADIM", "sortTypeApply Thread")
            if (getDao.getSortType().isNotEmpty()) {
                Log.e("VADIM", "sortTypeApply Thread into IF")
                val sortTypeEnumFromDb = db.getDaoSort().getSortType()[0].sortType
                if (sortTypeEnumFromDb == SortTypeEnum.DATE) {
                    sortTypeEnum = SortTypeEnum.PRIORITY
                } else {
                    Log.e("VADIM", "sortTypeApply Thread into Else")
                    sortTypeEnum = SortTypeEnum.DATE
                }
            } else {
                Log.e("VADIM", "sortTypeApply Thread into ELSE")
                val sortType = SortType(0, sortTypeEnum)
                getDao.insertSortType(sortType)
            }
        }.start()
        return sortTypeEnum
    }

//    fun themeChange(db: MainDb) {
//        Thread {
//            db.getDaoTheme().getTheme()
//        }//.start()
//        if () {
//            Thread {
//                db.getDaoTheme()
//            }//.start()
//        }
//    }

}