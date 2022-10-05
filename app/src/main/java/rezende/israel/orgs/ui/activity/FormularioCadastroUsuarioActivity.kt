package rezende.israel.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import rezende.israel.orgs.database.AppDataBase
import rezende.israel.orgs.databinding.ActivityFormularioCadastroUsuarioBinding
import rezende.israel.orgs.model.Usuario

class FormularioCadastroUsuarioActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioCadastroUsuarioBinding.inflate(layoutInflater)
    }

    private val dao by lazy {
        AppDataBase.instancia(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoCadastrar()
    }

    private fun configuraBotaoCadastrar() {
        binding.activityFormularioCadastroBotaoCadastrar.setOnClickListener {
            val novoUsuario = criaUsuario()
            lifecycleScope.launch {
                try {
                    dao.salva(novoUsuario)
                    finish()
                } catch (e: java.lang.Exception){
                    Log.e("CadastroUsuario", "configuraBotaoCadastrar: ", e)
                    Toast.makeText(this@FormularioCadastroUsuarioActivity, "Falha ao cadastrar usuário", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun criaUsuario(): Usuario {
        val usuario = binding.activityFormularioCadastroUsuario.text.toString()
        val nome = binding.activityFormularioCadastroNome.text.toString()
        val senha = binding.activityFormularioCadastroSenha.text.toString()
        return Usuario(usuario, nome, senha)
    }
}