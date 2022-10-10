package rezende.israel.orgs.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import rezende.israel.orgs.model.Produto

@Dao
interface ProdutoDAO {

    @Query("SELECT * FROM Produto WHERE usuarioId = :usuarioId")
    suspend fun buscaTodos(usuarioId: String): List<Produto>

    @Query("SELECT * FROM Produto")
    fun buscaTodosComFlow(): Flow<List<Produto>>

    @Query("SELECT * FROM Produto WHERE usuarioId = :usuarioId")
    fun buscaProdutosPorUsuario(usuarioId : String) : Flow<List<Produto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(vararg produto: Produto)

    @Delete
    suspend fun remove(vararg produto: Produto)

    @Query("SELECT * FROM Produto WHERE id = :id")
    fun buscaPorId(id: Long): Flow<Produto?>

    @Query("SELECT * FROM Produto WHERE usuarioId = :usuarioId ORDER BY nome DESC")
    suspend fun ordenaPorNomeDesc(usuarioId : String): List<Produto>

    @Query("SELECT * FROM Produto WHERE usuarioId = :usuarioId ORDER BY nome ASC")
    suspend fun ordenaPorNomeAsc(usuarioId : String): List<Produto>

    @Query("SELECT * FROM Produto WHERE usuarioId = :usuarioId ORDER BY descricao DESC")
    suspend fun ordenaPorDescricaoDesc(usuarioId : String): List<Produto>

    @Query("SELECT * FROM Produto WHERE usuarioId = :usuarioId ORDER BY descricao ASC")
    suspend fun ordenaPorDescricaoAsc(usuarioId : String): List<Produto>

    @Query("SELECT * FROM Produto WHERE usuarioId = :usuarioId ORDER BY valor DESC")
    suspend fun ordenaPorValorDesc(usuarioId : String): List<Produto>

    @Query("SELECT * FROM Produto WHERE usuarioId = :usuarioId ORDER BY valor ASC")
    suspend fun ordenaPorValorAsc(usuarioId : String): List<Produto>


}
