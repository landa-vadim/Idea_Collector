package domain.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import domain.dataClasses.Themes

@Dao
interface DaoTheme {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTheme(theme: Themes)
    @Query("SELECT * FROM themes")
    fun getTheme(): List<Themes>
}