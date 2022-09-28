package rezende.israel.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import rezende.israel.orgs.R
import rezende.israel.orgs.database.AppDataBase
import rezende.israel.orgs.databinding.ActivityDetalhesProdutoBinding
import rezende.israel.orgs.extensions.formataParaMoedaBr
import rezende.israel.orgs.extensions.tentaCarregarImagem
import rezende.israel.orgs.model.Produto

class DetalhesProdutoActivity : AppCompatActivity() {

    private var idProduto: Long? = null
    private var produto: Produto? = null
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }
    private val produtoDao by lazy {
        AppDataBase.instancia(this).produtoDao()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    override fun onResume() {
        super.onResume()
        idProduto?.let { id ->
            produto = produtoDao.buscaPorId(id)
        }
        produto?.let {
            preencheCampos(it)
        } ?: finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_datalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_detalhes_item_editar -> {
                Log.i("Menu", "onOptionsItemSelected: editar")
                Intent(this, FormularioProdutoActivity::class.java).apply {
                    putExtra(CHAVE_PRODUTO, produto)
                    startActivity(this)
                }
            }
            R.id.menu_detalhes_item_remover -> {
                produto?.let {
                    Log.i("Menu", "onOptionsItemSelected: remover")
                    produtoDao.remove(it)
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarProduto() {
        intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)?.let { produtoCarregado ->
            idProduto = produtoCarregado.id
        } ?: finish()
    }

    private fun preencheCampos(produtoCarregado: Produto) {
        with(binding) {
            activityDetalhesProdutoImagem.tentaCarregarImagem(produtoCarregado.imagem)
            activityDetalhesProdutoNome.text = produtoCarregado.nome
            activityDetalhesProdutoDescricao.text = produtoCarregado.descricao
            activityDetalhesProdutoValor.text = produtoCarregado.valor.formataParaMoedaBr()
        }
    }
}