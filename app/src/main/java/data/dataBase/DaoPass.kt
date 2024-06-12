package data.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import data.Password

@Dao
interface DaoPass {
    @Insert
    fun insertPass(password: Password)
    @Query("SELECT * FROM passwords")
    fun getPassword(): List<Password>
}