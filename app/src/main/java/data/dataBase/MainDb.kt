package data.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import data.Idea

@Database(entities = [Idea::class], version = 2)
abstract class MainDb : RoomDatabase() {
abstract fun getDao(): Dao

    companion object {
        fun getDb(context: Context): MainDb {
            return Room.databaseBuilder(
                context.applicationContext,
                MainDb::class.java,
                "ideas2.db"
            ).build()
        }
    }
}