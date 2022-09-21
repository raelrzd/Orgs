package rezende.israel.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import rezende.israel.orgs.R
import rezende.israel.orgs.dao.ProdutosDAO
import rezende.israel.orgs.ui.adapter.ListaProdutosAdapter

class ListaProdutosActivity : AppCompatActivity(R.layout.activity_lista_produtos) {

    private val dao = ProdutosDAO()
    private val adapter = ListaProdutosAdapter(context = this, produtos = dao.buscaTodos())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "App em construção... " + ("\u26A0"), Toast.LENGTH_LONG).show()
        configuraFab()
        configuraRecyclerView()
    }

    private fun configuraFab() {
        val fabAdd = findViewById<FloatingActionButton>(R.id.activity_lista_produtos_fab)
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
        adapter.atualiza(dao.buscaTodos())
    }

    private fun configuraRecyclerView() {
        val dao = ProdutosDAO()
        val recyclerView = findViewById<RecyclerView>(R.id.activity_lista_produtos_recyclerview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}