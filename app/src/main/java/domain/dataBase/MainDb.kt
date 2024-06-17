package domain.dataBase

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import domain.dataClasses.Idea
import domain.dataClasses.Password
import domain.dataClasses.SortType
import domain.dataClasses.Themes

@Database(
    entities = [Idea::class, Password::class, SortType::class, Themes::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [AutoMigration(from = 1, to = 2)]
)
abstract class MainDb : RoomDatabase() {
    abstract fun getDao(): Dao
    abstract fun getDaoPass(): DaoPass
    abstract fun getDaoSort(): DaoSort
    abstract fun getDaoTheme(): DaoTheme

    object DatabaseManager {
        private var instance: MainDb? = null

        fun getDb(context: Context): MainDb {
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

//    companion object {
//        fun getDb(context: Context): MainDb {
//            return Room.databaseBuilder(
//                context.applicationContext,
//                MainDb::class.java,
//                "ideas.db"
//            ).build()
//        }
//    }
}