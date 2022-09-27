package rezende.israel.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rezende.israel.orgs.database.converter.Converters
import rezende.israel.orgs.database.dao.ProdutoDAO
import rezende.israel.orgs.model.Produto

@Database(entities = [Produto::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun produtoDao(): ProdutoDAO

    companion object {
        fun instancia(context: Context): AppDataBase{
            return Room.databaseBuilder(
                context,
                AppDataBase::class.java,
                "orgs.db"
            ).allowMainThreadQueries().build()
        }
    }

}