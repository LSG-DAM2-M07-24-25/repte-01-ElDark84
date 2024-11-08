package com.example.androidstudio_koala_template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.graphics.Color
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.androidstudio_koala_template.ui.theme.AndroidStudioKoalaTemplateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Crear un LinearLayout vertical para organizar las vistas
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL // Colocar los elementos en columna
            setPadding(16, 16, 16, 16) // Opcional, para dar algo de espacio
        }

        // Crear el TextView con el título "Repte 01"
        val textView = TextView(this).apply {
            text = "Repte 01"
            textSize = 36f // tamaño del texto grande
            setTextColor(Color.BLUE) // color azul
            textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START // alineado a la izquierda
        }

        // Crear el botón que activará el menú desplegable
        val button = Button(this).apply {
            text = "Selecciona un icono"
        }

        // Configurar el botón para mostrar el PopupMenu (DropdownMenu)
        button.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)
            val menu = popupMenu.menu

            // Añadir opciones al menú desplegable
            menu.add("Agregar")
            menu.add("Llamar")
            menu.add("Email")
            menu.add("El resto de icono (Por defecto)")

            // Manejar la selección de una opción
            popupMenu.setOnMenuItemClickListener { item: MenuItem ->
                val opcionSeleccionada = item.title.toString()
                Toast.makeText(this, "Seleccionaste: $opcionSeleccionada", Toast.LENGTH_SHORT).show()
                true
            }

            // Mostrar el menú debajo del botón
            popupMenu.show()
        }

        // Agregar el TextView y el Button al layout
        layout.addView(textView)
        layout.addView(button)

        // Establecer el layout como la vista principal de la actividad
        setContentView(layout)
    }
}


