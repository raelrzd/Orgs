package rezende.israel.orgs.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rezende.israel.orgs.database.converter.Converters
import rezende.israel.orgs.database.dao.ProdutoDAO
import rezende.israel.orgs.model.Produto

@Database(entities = [Produto::class], version = 1 )
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDAO
}