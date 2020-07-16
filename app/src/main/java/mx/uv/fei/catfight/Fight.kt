package mx.uv.fei.catfight

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class Fight : AppCompatActivity()
{
    var vidaActual = 100
    var gatoActual = Monster(10)
    var kills = 0
    var lista = ArrayList<String>()
    lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fight)
        var Adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lista)
        val nombre =intent.getStringExtra("Nombre")
        val nombreDejugador = findViewById<TextView>(R.id.nombreYo)
        var vidaDeJugador = findViewById<ProgressBar>(R.id.vidaYo)
        var vidaTextoDeJugador = findViewById<TextView>(R.id.vidaTextoYo)
        val listaDeEventos = findViewById<ListView>(R.id.logDeEventos)
        listaDeEventos.adapter = Adapter
        adapter = Adapter
        vidaDeJugador.max = 100
        vidaDeJugador.progress = 100
        vidaTextoDeJugador.text = "100/100"
        nombreDejugador.text = "$nombre"
        gatoActual = seleccionarMounstro()
        AsignarMounstro(gatoActual)


    }


    fun buttonAtacar(view: View){
        val daño = Random.nextInt(5,10)
        gatoActual.RecibirDaño(daño)

        var eventoDeAtaque = getString(R.string.hiciste) + " " + daño + " " + getString(R.string.puntosDeDañoA) + " " + gatoActual.nombre
        lista.add(eventoDeAtaque)
        adapter.notifyDataSetChanged()
        if (!gatoActual.vivo){
            val killsTexto = findViewById<TextView>(R.id.kills)
            kills++
            killsTexto.text = getString(R.string.kills) + " " + kills
            gatoActual = seleccionarMounstro()
            var eventoDeMuerte = getString(R.string.matasteA) + " " + gatoActual.nombre
            lista.add(eventoDeMuerte)
            adapter.notifyDataSetChanged()
        } else {
            var daño =  gatoActual.Atacar(vidaActual)
            vidaActual -= daño
            val EventoDeDaño = getString(R.string.recibiste) + " " + daño + getString(R.string.puntosDeDañoDe) + " " + gatoActual.nombre

            lista.add(EventoDeDaño)
            adapter.notifyDataSetChanged()
            if (vidaActual <= 0 ){
                vidaActual = 0
                ActulizarInformacionDeJugador()
                val alerta: AlertDialog.Builder = AlertDialog.Builder(this)
                alerta.setMessage(getString(R.string.matasteA) + " " + kills + " " + getString(R.string.gatosInocentes))
                alerta.setTitle(getString(R.string.muerto))
                alerta.setPositiveButton("OK", DialogInterface.OnClickListener { _, _ -> this.finish(); })
                alerta.setCancelable(false)
                alerta.create().show()


            }
            ActulizarInformacionDeJugador()

        }
        AsignarMounstro(gatoActual)
    }

    fun buttonCurar(view: View){
        var curacion = Random.nextInt(1, 5)
        vidaActual += curacion
        if (vidaActual > 100){
            vidaActual = 100
        }
        var eventoDeCuracion = getString(R.string.curaste) + " " + curacion + " " + getString(R.string.puntosDeSalud);
        lista.add(eventoDeCuracion);
        adapter.notifyDataSetChanged();
        ActulizarInformacionDeJugador();
    }

    fun buttonRendirse(view: View){
        val alerta: AlertDialog.Builder = AlertDialog.Builder(this)
        alerta.setMessage(getString(R.string.matasteA) + " " + kills + " " + getString(R.string.gatosInocentes))
        alerta.setTitle(getString(R.string.rendir))
        alerta.setPositiveButton("OK", DialogInterface.OnClickListener { _, _ -> this.finish(); })
        alerta.setCancelable(false)
        alerta.create().show()
    }

    fun ActulizarInformacionDeJugador(){
        var vidaDeJugador = findViewById<ProgressBar>(R.id.vidaYo)
        var vidaTextoDeJugador = findViewById<TextView>(R.id.vidaTextoYo)
        vidaDeJugador.progress = vidaActual
        vidaTextoDeJugador.text = "$vidaActual" + "/100"
    }

    fun seleccionarMounstro():Monster{
        val valor = Random.nextInt(5)
        var monster = Monster(valor)
        return monster
    }

    fun AsignarMounstro(monster: Monster){
        val nombre = findViewById<TextView>(R.id.nombreMounstro)
        val vida = findViewById<ProgressBar>(R.id.vidaMounstro)
        val vidaTexto = findViewById<TextView>(R.id.vidaTextoGato)
        nombre.text = "${monster.nombre}"
        vida.max = monster.vida
        vida.progress = monster.vidaActual
        vidaTexto.text = "${monster.vidaActual}" + " / " + "${monster.vida}"
    }
}

class Monster
{
    var nombre = "missingno"
    var vida = 1337
    var vidaActual = 1337
    var dañoMaximo = 1000
    var vivo = true

    constructor(n: Int)
    {
        if(n ==0){
            this.nombre = "Gato enfermo"
            this.vida = 10
            this.vidaActual = this.vida
            this.dañoMaximo = 2
        } else if(n ==1){
            this.nombre = "Gato peludo"
            this.vida = 30
            this.vidaActual = this.vida
            this.dañoMaximo = 10
        } else if(n == 2){
            this.nombre = "Gato enojado"
            this.vida = 35
            this.vidaActual = this.vida
            this.dañoMaximo = 12
        } else if(n == 3){
            this.nombre = "Gato negro"
            this.vida = 40
            this.vidaActual = this.vida
            this.dañoMaximo = 13
        } else if(n == 4){
            this.nombre = "Gato con garras afiladas"
            this.vida = 45
            this.vidaActual = this.vida
            this.dañoMaximo = 15
        } else if(n == 5){
            this.nombre = "Gato rabioso"
            this.vida = 50
            this.vidaActual = this.vida
            this.dañoMaximo = 20
        } else {
            this.nombre = "misingNo"
            this.vida = 1337
            this.vidaActual = this.vida
            this.dañoMaximo = 1000
        }
    }

    fun RecibirDaño(daño: Int){
        vidaActual = vidaActual-daño
        if(vidaActual <= 0){
            vivo = false
        }
    }

    fun Atacar(vidaDejugador: Int): Int{
        return Random.nextInt(1, dañoMaximo)
    }
}
