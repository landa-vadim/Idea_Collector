package data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "themes")
data class Themes (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "sortType")
    val theme: ThemeEnum
)

enum class ThemeEnum {
    LIGHT,
    DARK
}