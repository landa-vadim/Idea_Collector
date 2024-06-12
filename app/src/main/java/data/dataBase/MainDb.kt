package data.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import data.Idea
import data.Password

@Database(
    entities = [Idea::class, Password::class],
    version = 1,
    exportSchema = true
)
abstract class MainDb : RoomDatabase() {
    abstract fun getDao(): Dao
    abstract fun getDaoPass(): DaoPass

    companion object {
        fun getDb(context: Context): MainDb {
            return Room.databaseBuilder(
                context.applicationContext,
                MainDb::class.java,
                "ideas.db"
            ).build()
        }
    }
}