package rezende.israel.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import rezende.israel.orgs.dao.ProdutosDAO
import rezende.israel.orgs.database.AppDataBase
import rezende.israel.orgs.database.dao.ProdutoDAO
import rezende.israel.orgs.databinding.ActivityListaProdutosBinding
import rezende.israel.orgs.model.Produto
import rezende.israel.orgs.ui.adapter.ListaProdutosAdapter
import java.math.BigDecimal

class ListaProdutosActivity : AppCompatActivity() {

    private val dao = ProdutosDAO()
    private val adapter = ListaProdutosAdapter(produtos = dao.buscaTodos(), context = this,)
    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "App em construção... " + ("\u26A0"), Toast.LENGTH_LONG).show()
        setContentView(binding.root)
        configuraFab()
        configuraRecyclerView()

        val db = Room.databaseBuilder(this, AppDataBase::class.java, "orgs.db").allowMainThreadQueries().build()
        val produtoDao = db.produtoDao()
//        produtoDao.salva(Produto(nome = "Teste Nome 2", descricao = "Teste 2 desc", valor = BigDecimal(25.99)))
        adapter.atualiza(produtoDao.buscaTodos())
    }

    private fun configuraFab() {
        val fabAdd = binding.activityListaProdutosFab
        fabAdd.setOnClickListener {
            vaiParaFormProduto()
        }
    }

    private fun vaiParaFormProduto() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
//        adapter.atualiza(dao.buscaTodos())
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityListaProdutosRecyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter.quandoClicaNoItem = {
            val intent = Intent(this, DetalhesProdutoActivity::class.java).apply {
                putExtra(CHAVE_PRODUTO, it)
            }
            startActivity(intent)
        }

    }
}