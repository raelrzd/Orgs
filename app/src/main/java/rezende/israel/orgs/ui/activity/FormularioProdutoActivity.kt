package rezende.israel.orgs.ui.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import rezende.israel.orgs.database.AppDataBase
import rezende.israel.orgs.databinding.ActivityFormularioProdutoBinding
import rezende.israel.orgs.extensions.tentaCarregarImagem
import rezende.israel.orgs.model.Produto
import rezende.israel.orgs.ui.dialog.FormularioImagemDialog
import java.math.BigDecimal

class FormularioProdutoActivity : UsuarioBaseActivity() {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }

    private val produtoDao by lazy {
        AppDataBase.instancia(this).produtoDao()
    }

    private var url: String? = null
    private var idProduto = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar produto"
        configuraBotaoSalvar()

        binding.activityFormularioProdutoImagem.setOnClickListener {
            FormularioImagemDialog(this).mostra(url) { imagem ->
                url = imagem
                binding.activityFormularioProdutoImagem.tentaCarregarImagem(url)
            }
        }
        tentaCarregarProduto()
        tentaBuscarProduto()
    }

    private fun tentaCarregarProduto() {
        idProduto = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
    }

    private fun tentaBuscarProduto() {
        lifecycleScope.launch {
            produtoDao.buscaPorId(idProduto).collect {
                it?.let {
                    title = "Alterar produto"
                    preencheCamos(it)
                }
            }
        }
    }

    private fun preencheCamos(produto: Produto) {
        url = produto.imagem
        binding.activityFormularioProdutoImagem.tentaCarregarImagem(produto.imagem)
        binding.activityFormularioProdutoNome.setText(produto.nome)
        binding.activityFormularioProdutoDescricao.setText(produto.descricao)
        binding.activityFormularioProdutoValor.setText(produto.valor.toPlainString())
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityFormularioProdutoSalvar
        botaoSalvar.setOnClickListener {
            usuario.value?.let {
                val novoProduto = criaProduto(it.id)
                lifecycleScope.launch {
                    produtoDao.salva(novoProduto)
                    finish()
                }
            }
        }
    }

    private fun criaProduto(usuarioId: String): Produto {
        val campoNome = binding.activityFormularioProdutoNome
        val nome = campoNome.text.toString()
        val campoDescricao = binding.activityFormularioProdutoDescricao
        val descricao = campoDescricao.text.toString()
        val campoValor = binding.activityFormularioProdutoValor
        val valorEmTexto = campoValor.text.toString()
        val valor = if (valorEmTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorEmTexto)
        }
        return Produto(
            id = idProduto,
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url,
            usuarioId = usuarioId
        )
    }
}