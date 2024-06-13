package data.dataBase

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.android.material.color.utilities.Scheme
import data.Idea
import data.Password
import data.SortType
import data.Theme

@Database(
    entities = [Idea::class, Password::class, SortType::class, Theme::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [AutoMigration(from = 1, to = 2)]
)
abstract class MainDb : RoomDatabase() {
    abstract fun getDao(): Dao
    abstract fun getDaoPass(): DaoPass
    abstract fun getDaoSort(): DaoSort
    abstract fun getDaoTheme(): DaoTheme

    companion object {
        fun getDb(context: Context): MainDb {
            return Room.databaseBuilder(
                context.applicationContext, MainDb::class.java, "ideas.db"
            ).build()
        }
    }
}