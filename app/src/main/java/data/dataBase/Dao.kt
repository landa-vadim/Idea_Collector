package data.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import data.Idea

@Dao
interface Dao {
    @Insert
    fun insertItem(item: Idea)
    @Query("SELECT * FROM ideas")
    fun getAllItems(): List<Idea>
}