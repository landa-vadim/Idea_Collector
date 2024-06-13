package data.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import data.Theme

@Dao
interface DaoTheme {
    @Insert
    fun insertTheme(theme: Theme)
    @Query("SELECT * FROM themes")
    fun getTheme(): List<Theme>
}