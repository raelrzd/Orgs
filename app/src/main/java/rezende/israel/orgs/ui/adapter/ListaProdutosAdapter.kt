package rezende.israel.orgs.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import rezende.israel.orgs.R
import rezende.israel.orgs.databinding.ProdutoItemBinding
import rezende.israel.orgs.extensions.formataParaMoedaBr
import rezende.israel.orgs.extensions.tentaCarregarImagem
import rezende.israel.orgs.model.Produto

class ListaProdutosAdapter(
    produtos: List<Produto> = emptyList(),
    private val context: Context,
    var quandoClicaNoItem: (produto: Produto) -> Unit = {},
    var quandoClicaNoEditar: (produto: Produto) -> Unit = {},
    var quandoClicaNoRemover: (produto: Produto) -> Unit = {}
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

    private val produtos = produtos.toMutableList()

    inner class ViewHolder(private val binding: ProdutoItemBinding) :
        RecyclerView.ViewHolder(binding.root), PopupMenu.OnMenuItemClickListener {

        private lateinit var produto: Produto

        init {
            itemView.setOnClickListener {
                if (::produto.isInitialized) {
                    quandoClicaNoItem(produto)
                }
            }

            itemView.setOnLongClickListener {
                PopupMenu(context, itemView).apply {
                    menuInflater.inflate(R.menu.menu_datalhes_produto, menu)
                    setOnMenuItemClickListener(this@ViewHolder)
                }.show()
                true
            }
        }


        fun vincula(produto: Produto) {
            this.produto = produto
            val nome = binding.produtoItemNome
            nome.text = produto.nome
            val descricao = binding.produtoItemDescricao
            descricao.text = produto.descricao
            val valor = binding.produtoItemValor
            val valorEmMoedaBr: String = produto.valor.formataParaMoedaBr()
            valor.text = valorEmMoedaBr

            val visibilidade = if (produto.imagem != null) {
                View.VISIBLE
            } else {
                View.GONE
            }

            binding.produtoItemImageview.visibility = visibilidade
            binding.produtoItemImageview.tentaCarregarImagem(produto.imagem)
        }

        override fun onMenuItemClick(item: MenuItem?): Boolean {
            item?.let {
                when (it.itemId) {
                    R.id.menu_detalhes_item_editar -> {
                        quandoClicaNoEditar(produto)
                    }
                    R.id.menu_detalhes_item_remover -> {
                        quandoClicaNoRemover(produto)
                    }
                }
            }
            return true
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ProdutoItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto = produtos[position]
        holder.vincula(produto)
    }

    override fun getItemCount(): Int = produtos.size

    fun atualiza(produtos: List<Produto>) {
        this.produtos.clear()
        this.produtos.addAll(produtos)
        notifyDataSetChanged()
    }
}
