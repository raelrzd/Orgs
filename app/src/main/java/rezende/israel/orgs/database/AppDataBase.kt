package rezende.israel.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rezende.israel.orgs.database.converter.Converters
import rezende.israel.orgs.database.dao.ProdutoDAO
import rezende.israel.orgs.database.dao.UsuarioDAO
import rezende.israel.orgs.database.migration.MIGRATION_1_2
import rezende.israel.orgs.model.Produto
import rezende.israel.orgs.model.Usuario

@Database(entities = [Produto::class, Usuario::class], version = 2, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun produtoDao(): ProdutoDAO
    abstract fun usuarioDao(): UsuarioDAO

    companion object {
        @Volatile var db: AppDataBase? = null
        fun instancia(context: Context): AppDataBase {
            return db ?: Room.databaseBuilder(
                context,
                AppDataBase::class.java,
                "orgs.db"
            ).addMigrations(MIGRATION_1_2).build().also {
                db = it
            }
        }
    }
}