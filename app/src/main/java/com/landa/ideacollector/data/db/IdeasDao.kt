package com.landa.ideacollector.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.landa.ideacollector.domain.model.Idea
import kotlinx.coroutines.flow.Flow

@Dao
interface IdeasDao {
    @Insert
    suspend fun insertIdea(idea: Idea)
    @Query("SELECT * FROM ideas")
    fun getAllIdeas(): Flow<List<Idea>>
    @Query("DELETE FROM ideas WHERE Id = :id")
    suspend fun deleteIdea(id: Int?)
    @Update
    suspend fun editIdea(idea: Idea)
}