package com.landa.ideacollector.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.landa.ideacollector.domain.model.Idea

@Database(
    entities = [Idea::class],
    version = 1,
    exportSchema = true,
)
abstract class MainDb : RoomDatabase() {
    abstract fun getIdeasDao(): IdeasDao

    companion object DatabaseManager {
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
}