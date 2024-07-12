package domain.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import domain.dataClasses.Idea
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    fun insertIdea(item: Idea)
    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<Idea>>
}