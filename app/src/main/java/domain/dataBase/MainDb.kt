package domain.dataBase

import android.content.Context
import android.util.Log
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import domain.dataClasses.Idea
import domain.dataClasses.Password
import domain.dataClasses.SortType
import domain.dataClasses.Themes
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [Idea::class, Password::class],
    version = 1,
    exportSchema = true,
)
abstract class MainDb : RoomDatabase() {
    abstract fun getDao(): Dao
    abstract fun getDaoPass(): DaoPass

    companion object DatabaseManager {
        private var instance: MainDb? = null

        fun getDb(context: Context, scope: CoroutineScope): MainDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): MainDb {
            return Room.databaseBuilder(
                context.applicationContext,
                MainDb::class.java, "ideas.db"
            ).fallbackToDestructiveMigration()
                .build()
        }
    }
}