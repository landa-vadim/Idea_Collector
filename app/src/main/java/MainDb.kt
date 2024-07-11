import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Idea::class, Password::class],
    version = 1,
    exportSchema = true,
)
abstract class MainDb : RoomDatabase() {
    abstract fun getDao(): Dao
    abstract fun getDaoPass(): DaoPass

    companion object DatabaseManager {
        @Volatile
        private var INSTANCE: MainDb? = null

        fun getDb(context: Context): MainDb {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
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