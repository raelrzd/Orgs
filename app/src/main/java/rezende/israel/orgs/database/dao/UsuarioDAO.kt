package rezende.israel.orgs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import rezende.israel.orgs.model.Usuario

@Dao
interface UsuarioDAO {

    @Insert
    suspend fun salva(usuario: Usuario)

    @Query("SELECT * FROM usuario WHERE id = :usuarioId AND senha = :senha")
    suspend fun autentica(usuarioId: String, senha: String) : Usuario?

    @Query("SELECT * FROM usuario WHERE id = :usuarioId")
    fun buscaPorId(usuarioId: String) : Flow<Usuario>

}