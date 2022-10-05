package rezende.israel.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import rezende.israel.orgs.database.AppDataBase
import rezende.israel.orgs.databinding.ActivityLoginBinding
import rezende.israel.orgs.extensions.vaiPara

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val usuarioDao by lazy {
        AppDataBase.instancia(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Toast.makeText(this, "Seja Bem Vindo!! " + ("\uD83E\uDD19"), Toast.LENGTH_LONG).show()
        configuraBotaoCadastrar()
        configuraBotaoEntrar()
    }

    private fun configuraBotaoEntrar() {
        binding.activityLoginBotaoEntrar.setOnClickListener {
            val usuario = binding.activityLoginUsuario.text.toString()
            val senha = binding.activityLoginSenha.text.toString()
            lifecycleScope.launch {
                usuarioDao.autentica(usuario, senha)?.let { usuario ->
                    Log.i("LoginActivity", "onCreate: $usuario - $senha")
                    vaiPara(ListaProdutosActivity::class.java) {
                        putExtra("CHAVE_USUARIO_ID", usuario.id)
                    }
                } ?: Toast.makeText(this@LoginActivity, "Falha na autenticação", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun configuraBotaoCadastrar() {
        binding.activityLoginBotaoCadastrar.setOnClickListener {
            vaiPara(FormularioCadastroUsuarioActivity::class.java)
        }
    }

}