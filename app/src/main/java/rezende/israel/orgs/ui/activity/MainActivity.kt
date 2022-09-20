package rezende.israel.orgs.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import rezende.israel.orgs.R
import rezende.israel.orgs.model.Produto
import rezende.israel.orgs.ui.adapter.ListaProdutosAdapter
import java.math.BigDecimal

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "App em construção... " + ("\u26A0"), Toast.LENGTH_LONG).show()


//        val nome = findViewById<TextView>(R.id.nome)
//        nome.text = "Cesta de Frutas"
//        val descricao = findViewById<TextView>(R.id.descricao)
//        descricao.text = "Laranja, morango e uva"
//        val valor = findViewById<TextView>(R.id.valor)
//        valor.text = "R$ 19.90"

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = ListaProdutosAdapter(
            context = this, produtos = listOf(
                Produto(
                    nome = "Teste 1",
                    descricao = "Descricao teste 1",
                    valor = BigDecimal("19.99")
                ), Produto(
                    nome = "Teste 2",
                    descricao = "Descricao teste 2",
                    valor = BigDecimal("29.99")
                ), Produto(
                    nome = "Teste 3",
                    descricao = "Descricao teste 3",
                    valor = BigDecimal("39.99")
                )
            )
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}