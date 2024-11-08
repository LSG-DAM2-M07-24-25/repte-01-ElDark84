package com.example.androidstudio_koala_template

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast

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

            // Personalizar el color de fondo del PopupMenu
            try {
                val fieldBackground = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldBackground.isAccessible = true
                val popup = fieldBackground.get(popupMenu)

                // Cambiar el fondo del menú
                popup.javaClass.getDeclaredMethod("setBackgroundDrawable", android.graphics.drawable.Drawable::class.java)
                    .invoke(popup, ColorDrawable(Color.BLUE))

                // Cambiar el color de fondo de los items de menú
                for (i in 0 until menu.size()) {
                    val item = menu.getItem(i)
                    // Cambiar el color del texto de los ítems usando reflexion para el menú
                    val itemView = item.actionView
                    itemView?.setBackgroundColor(Color.BLUE) // Fondo azul para los ítems
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

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


