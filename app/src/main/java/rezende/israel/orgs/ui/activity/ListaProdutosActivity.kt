package rezende.israel.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import rezende.israel.orgs.R
import rezende.israel.orgs.database.AppDataBase
import rezende.israel.orgs.databinding.ActivityListaProdutosBinding
import rezende.israel.orgs.extensions.vaiPara
import rezende.israel.orgs.model.Produto
import rezende.israel.orgs.ui.adapter.ListaProdutosAdapter

class ListaProdutosActivity : UsuarioBaseActivity() {

    private val adapter = ListaProdutosAdapter(context = this)

    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    private val produtoDao by lazy {
        AppDataBase.instancia(this).produtoDao()
    }

    private lateinit var idUsuarioLogado: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraFab()
        configuraRecyclerView()

        lifecycleScope.launch {
            usuario.filterNotNull().collect { usuario ->
                idUsuarioLogado = usuario.id
                Toast.makeText(
                    this@ListaProdutosActivity,
                    "Bem vindo de volta ${usuario.nome}!" + ("\uD83E\uDD19"),
                    Toast.LENGTH_LONG
                ).show()
                buscaProdutosUsuario(usuario.id)
            }
        }
    }

    private suspend fun buscaProdutosUsuario(usuarioId: String) {
        idUsuarioLogado = usuarioId
        produtoDao.buscaProdutosPorUsuario(usuarioId).collect { produtos ->
            adapter.atualiza(produtos)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_produtos_ordenacao, menu)
        menuInflater.inflate(R.menu.menu_perfil_lista_produtos, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var produtosOrdenado: List<Produto>?
        lifecycleScope.launch {
            produtosOrdenado = when (item.itemId) {
                R.id.menu_ordenacao_item_nome_desc ->
                    produtoDao.ordenaPorNomeDesc(idUsuarioLogado)
                R.id.menu_ordenacao_item_nome_asc ->
                    produtoDao.ordenaPorNomeAsc(idUsuarioLogado)
                R.id.menu_ordenacao_item_desc_desc ->
                    produtoDao.ordenaPorDescricaoDesc(idUsuarioLogado)
                R.id.menu_ordenacao_item_desc_asc ->
                    produtoDao.ordenaPorDescricaoAsc(idUsuarioLogado)
                R.id.menu_ordenacao_item_valor_desc ->
                    produtoDao.ordenaPorValorDesc(idUsuarioLogado)
                R.id.menu_ordenacao_item_valor_asc ->
                    produtoDao.ordenaPorValorAsc(idUsuarioLogado)
                R.id.menu_ordenacao_item_sem_ordenacao ->
                    produtoDao.buscaTodos(idUsuarioLogado)
                else -> null
            }
            produtosOrdenado?.let {
                adapter.atualiza(it)
            }
            when (item.itemId) {
                R.id.menu_perfil_lista_produtos ->
                    vaiPara(PerfilUsuarioActivity::class.java)
            }
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
            lifecycleScope.launch {
                produtoDao.remove(it)
            }
        }
    }
}