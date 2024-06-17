package domain.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import domain.dataClasses.SortType

@Dao
interface DaoSort {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSortType(sortBy: SortType)
    @Query("SELECT * FROM sortTypes")
    fun getSortType(): List<SortType>
}