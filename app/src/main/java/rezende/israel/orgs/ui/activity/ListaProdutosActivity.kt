package rezende.israel.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rezende.israel.orgs.R
import rezende.israel.orgs.database.AppDataBase
import rezende.israel.orgs.databinding.ActivityListaProdutosBinding
import rezende.israel.orgs.model.Produto
import rezende.israel.orgs.ui.adapter.ListaProdutosAdapter

class ListaProdutosActivity : AppCompatActivity() {

    private val adapter = ListaProdutosAdapter(context = this)
    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    private val produtoDao by lazy {
        AppDataBase.instancia(this).produtoDao()
    }
    private val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "Seja Bem Vindo!! " + ("\uD83E\uDD19"), Toast.LENGTH_LONG).show()
        setContentView(binding.root)
        configuraFab()
        configuraRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_produtos_ordenacao, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val produtosOrdenado: List<Produto>? = when (item.itemId) {
            R.id.menu_ordenacao_item_nome_desc ->
                produtoDao.ordenaPorNomeDesc()
            R.id.menu_ordenacao_item_nome_asc ->
                produtoDao.ordenaPorNomeAsc()
            R.id.menu_ordenacao_item_desc_desc ->
                produtoDao.ordenaPorDescricaoDesc()
            R.id.menu_ordenacao_item_desc_asc ->
                produtoDao.ordenaPorDescricaoAsc()
            R.id.menu_ordenacao_item_valor_desc ->
                produtoDao.ordenaPorValorDesc()
            R.id.menu_ordenacao_item_valor_asc ->
                produtoDao.ordenaPorValorAsc()
            R.id.menu_ordenacao_item_sem_ordenacao ->
                produtoDao.buscaTodos()
            else -> null
        }
        produtosOrdenado?.let {
            adapter.atualiza(it)
        }
        return super.onOptionsItemSelected(item)
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
        scope.launch {
            val produtos = withContext(Dispatchers.IO) {
                produtoDao.buscaTodos()
            }
            adapter.atualiza(produtos)
        }
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityListaProdutosRecyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter.quandoClicaNoItem = {
            Intent(this, DetalhesProdutoActivity::class.java).apply {
                putExtra(CHAVE_PRODUTO_ID, it.id)
                startActivity(this)
            }
        }

        adapter.quandoClicaNoEditar = {
            Intent(this, FormularioProdutoActivity::class.java).apply {
                putExtra(CHAVE_PRODUTO_ID, it.id)
                startActivity(this)
            }
        }

        adapter.quandoClicaNoRemover = {
            scope.launch {
                val produtos = withContext(Dispatchers.IO){
                    produtoDao.remove(it)
                    produtoDao.buscaTodos()
                }
                adapter.atualiza(produtos)
            }
        }
    }
}