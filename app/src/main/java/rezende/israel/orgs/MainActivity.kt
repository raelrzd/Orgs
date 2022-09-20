package rezende.israel.orgs

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "App em construção... " + ("\u26A0"), Toast.LENGTH_LONG).show()
        setContentView(R.layout.activity_main)

        val nome = findViewById<TextView>(R.id.nome)
        nome.text = "Cesta de Frutas"
        val descricao = findViewById<TextView>(R.id.descricao)
        descricao.text = "Laranja, morango e uva"
        val valor = findViewById<TextView>(R.id.valor)
        valor.text = "R$ 19,90"

    }
}