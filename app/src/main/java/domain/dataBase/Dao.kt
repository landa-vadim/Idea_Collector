package domain.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import domain.dataClasses.Idea

@Dao
interface Dao {
    @Insert
    fun insertIdea(item: Idea)
    @Query("SELECT * FROM items")
    fun getAllItems(): List<Idea>
}