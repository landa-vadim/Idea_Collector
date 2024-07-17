package com.landa.ideacollector.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.landa.ideacollector.domain.model.Password

@Dao
interface DaoPass {
    @Insert
    fun insertPass(password: Password)
    @Query("SELECT * FROM passwords")
    fun getPassword(): List<Password>
}