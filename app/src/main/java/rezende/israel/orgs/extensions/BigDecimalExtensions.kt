package rezende.israel.orgs.extensions

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

fun BigDecimal.formataParaMoedaBr(): String {
    val formatador: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
    return formatador.format(this)
}