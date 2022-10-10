package rezende.israel.orgs.ui.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import rezende.israel.orgs.databinding.ActivityPerfilUsuarioBinding


class PerfilUsuarioActivity : UsuarioBaseActivity() {

    private val binding by lazy {
        ActivityPerfilUsuarioBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Perfil")
        setContentView(binding.root)
        preencheUsuarioLogado()
        configuraBotaoSair()
    }

    private fun preencheUsuarioLogado() {
        val nome = binding.activityAcessoUsuarioUser
        lifecycleScope.launch {
            launch {
                usuario.filterNotNull().collect {
                    nome.setText(it.nome)
                }
            }
        }
    }

    private fun configuraBotaoSair() {
        val botaoSair = binding.activityAcessoUsuarioBotaoSair
        botaoSair.setOnClickListener {
            lifecycleScope.launch {
                deslogaUsuario()
            }
        }
    }
}