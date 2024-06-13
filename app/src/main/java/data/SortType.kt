package data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sortTypes")
data class SortType (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "sortType")
    val sortType: SortTypeEnum
)

enum class SortTypeEnum {
    DATE,
    PRIORITY
}