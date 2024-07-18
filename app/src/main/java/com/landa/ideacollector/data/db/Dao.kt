package com.landa.ideacollector.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.landa.ideacollector.domain.model.Idea
import kotlinx.coroutines.flow.Flow

@Dao
interface IdeasDao {
    @Insert
    suspend fun insertIdea(item: Idea)
    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<Idea>>
}