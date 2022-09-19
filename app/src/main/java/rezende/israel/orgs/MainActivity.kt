package rezende.israel.orgs

import android.app.Activity
import android.os.Bundle
import android.widget.Toast

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, "App em construção... "+ ("\u26A0"), Toast.LENGTH_LONG).show()
    }

}