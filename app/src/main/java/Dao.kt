import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface Dao {
    @Insert
    suspend fun insertIdea(item: Idea)

    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<Idea>>
}