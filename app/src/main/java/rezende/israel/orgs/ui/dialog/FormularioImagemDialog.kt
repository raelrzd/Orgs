package rezende.israel.orgs.ui.dialog

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import rezende.israel.orgs.databinding.FormularioImagemBinding
import rezende.israel.orgs.extensions.tentaCarregarImagem

class FormularioImagemDialog(private val context: Context) {

    fun mostra(
        urlPadrao: String? = null,
        quandoImagemCarregada: (imagem: String) -> Unit
    ) {
        FormularioImagemBinding.inflate(LayoutInflater.from(context)).apply {

            urlPadrao?.let {
                formularioImagemImageview.tentaCarregarImagem(it)
                formularioImagemUrl.setText(it)
            }

            formularioImagemBotaoCarregar.setOnClickListener {
                val url = formularioImagemUrl.text.toString()
                formularioImagemImageview.tentaCarregarImagem(url)
            }

            AlertDialog.Builder(context)
                .setView(root)
                .setPositiveButton("Confirmar") { _, _ ->
                    val url = formularioImagemUrl.text.toString()
                    Log.i("FormularioImagemDialog", "mostra: $url")
                    quandoImagemCarregada(url)
                }
                .setNegativeButton("Cancelar") { _, _ -> }
                .show()
        }
    }
}
