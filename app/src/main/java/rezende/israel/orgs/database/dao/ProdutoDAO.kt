package rezende.israel.orgs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import rezende.israel.orgs.model.Produto

@Dao
interface ProdutoDAO {

    @Query("SELECT * FROM Produto")
    fun buscaTodos() : List<Produto>

    @Insert
    fun salva(vararg produto: Produto)


}