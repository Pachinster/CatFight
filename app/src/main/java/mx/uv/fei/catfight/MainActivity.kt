package mx.uv.fei.catfight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun clickButtonAmonos(view: View)
    {
        var elementoDeTexto = findViewById<EditText>(R.id.miNombre)
        var resultado = "";
        var error = findViewById<TextView>(R.id.error);

        val nombreIngresado = elementoDeTexto.text;
        resultado = "" + elementoDeTexto.text;
        if(nombreIngresado.isNullOrEmpty()){
            error.visibility = View.VISIBLE;
        } else {
            error.visibility = View.GONE;
            val intent = Intent(this@MainActivity,Fight::class.java)
            intent.putExtra("Nombre",resultado)
            startActivity(intent)
        }
    }

}
