package rezende.israel.orgs.database.dao

import androidx.room.*
import rezende.israel.orgs.model.Produto

@Dao
interface ProdutoDAO {

    @Query("SELECT * FROM Produto")
    suspend fun buscaTodos(): List<Produto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(vararg produto: Produto)

    @Delete
    suspend fun remove(vararg produto: Produto)

    @Query("SELECT * FROM Produto WHERE id = :id")
    suspend fun buscaPorId(id: Long): Produto?

    @Query("SELECT * FROM Produto ORDER BY nome DESC")
    suspend fun ordenaPorNomeDesc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY nome ASC")
    suspend fun ordenaPorNomeAsc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY descricao DESC")
    suspend fun ordenaPorDescricaoDesc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY descricao ASC")
    suspend fun ordenaPorDescricaoAsc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY valor DESC")
    suspend fun ordenaPorValorDesc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY valor ASC")
    suspend fun ordenaPorValorAsc(): List<Produto>


}
