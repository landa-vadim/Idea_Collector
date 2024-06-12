package data.dataBase

//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import data.Password
//
//class PasswordDb {
//    @Database(entities = [Password::class], version = 1)
//    abstract class PasswordDb : RoomDatabase() {
//        abstract fun getDao(): DaoPass
//
//        companion object {
//            fun getDb(context: Context): PasswordDb {
//                return Room.databaseBuilder(
//                    context.applicationContext,
//                    MainDb::class.java,
//                    "passwords.db"
//                ).build()
//            }
//        }
//    }
//}