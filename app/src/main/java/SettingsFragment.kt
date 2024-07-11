import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.preference.PreferenceFragmentCompat
import com.landa.ideacollector.R
import utils.DataModel

class SettingsFragment : PreferenceFragmentCompat() {

    private val dataModel: DataModel by activityViewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        preferenceScreen.getPreference(2).setOnPreferenceClickListener {
            dataModel.setNewPassword.value = true
            it.setSummary("Password is SET!")
            true
        }
        preferenceScreen.getPreference(3).setOnPreferenceClickListener {
            dataModel.sortTypeChange.value = true
            dataModel.sortTypeChoice.observe(this, { sortType ->
                it.setSummary(sortType)
            })
            true
        }
        preferenceScreen.getPreference(4).setOnPreferenceClickListener {
            dataModel.themeChange.value = true
            dataModel.themeChoice.observe(this, { theme ->
                it.setSummary(theme)
            })
            true
        }
        return
    }


}

