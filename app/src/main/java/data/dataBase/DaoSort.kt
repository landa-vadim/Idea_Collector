package data.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import data.SortType

@Dao
interface DaoSort {
    @Insert
    fun insertSortType(sortBy: SortType)
    @Query("SELECT * FROM sortTypes")
    fun getSortType(): List<SortType>
}