package rezende.israel.orgs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import rezende.israel.orgs.model.Usuario

@Dao
interface UsuarioDAO {

    @Insert
    suspend fun salva(usuario: Usuario)

}