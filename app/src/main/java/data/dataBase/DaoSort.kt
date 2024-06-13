package data.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.SortType
import data.SortTypeEnum

@Dao
interface DaoSort {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSortType(sortType: SortType)
    @Query("SELECT * FROM sortTypes")
    fun getSortType(): List<SortType>
}