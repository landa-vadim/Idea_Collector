package domain.utilityClasses

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.IdeasRepository
import data.dataClasses.Idea
import data.dataClasses.Priority
import data.dataClasses.SortTypeEnum
import data.dataClasses.ThemeEnum
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date

class IdeasViewModel(private val repository: IdeasRepository) : ViewModel() {
    private val ideasListFlow = repository.ideasList.stateIn(
        viewModelScope, SharingStarted.Lazily, emptyList()
    )
    private val sortedType = repository.sortedType.stateIn(
        viewModelScope, SharingStarted.Lazily, SortTypeEnum.DATE
    )
    private val theme = repository.theme.stateIn(
        viewModelScope, SharingStarted.Lazily, ThemeEnum.LIGHT
    )

    fun getSortedIdeas() = ideasListFlow

    fun userClickedDoneButton(string: String, color: Int) {
        val ideaPriority = when (color) {
            0 -> Priority.HIGH
            1 -> Priority.MEDIUM
            2 -> Priority.LOW
            else -> return
        }
        val ideaText = string
        val ideaDate = Date().toString()
        val idea = Idea(
            null, ideaPriority, ideaText, ideaDate
        )
        viewModelScope.launch { repository.insertIdea(idea) }
    }

    fun userSwitchedPassCheckBox(switch: Boolean) {
        repository.passCheckBoxSwitch(switch)
    }

    fun passCheckBoxState(): Boolean = repository.passCheckBoxState

    suspend fun changeSortType() {
        sortedType.collect { value ->
            Log.e("VADIM", "collectingSortType")
            val setValue =
                when (value) {
                    SortTypeEnum.DATE -> SortTypeEnum.PRIORITY
                    SortTypeEnum.PRIORITY -> SortTypeEnum.DATE
                }
            setSortedType(setValue)
        }
    }

    private suspend fun setSortedType(sortTypeEnum: SortTypeEnum) {
        repository.setSortedType(sortTypeEnum)
    }

    suspend fun changeTheme() {
        theme.collect { value ->
            Log.e("VADIM", "collectingTheme")
            val setValue =
                when (value) {
                    ThemeEnum.LIGHT -> ThemeEnum.DARK
                    ThemeEnum.DARK -> ThemeEnum.LIGHT
                }
            setTheme(setValue)
        }
    }

    private suspend fun setTheme(themeEnum: ThemeEnum) {
        repository.setTheme(themeEnum)
    }

    fun getSortedType() = sortedType
    fun getTheme() = theme

}