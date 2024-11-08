package com.example.androidstudio_koala_template

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.MenuItem
import android.widget.*
import android.view.View
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.view.Gravity
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Crear un LinearLayout vertical para organizar las vistas
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL // Colocar los elementos en columna
            setPadding(16, 16, 16, 16) // Opcional, para dar algo de espacio
            gravity = Gravity.CENTER_HORIZONTAL // Centrar horizontalmente todos los elementos
        }

        // Crear el TextView con el título "Repte 01"
        val textView = TextView(this).apply {
            text = "Repte 01"
            textSize = 36f // tamaño del texto grande
            setTextColor(Color.BLUE) // color azul
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER // Centrar el texto
        }

        // Crear el botón que activará el menú desplegable
        val button = Button(this).apply {
            text = "Tria un Icon"
            gravity = Gravity.CENTER
        }

        // Crear los TextViews para Min y Max
        val minTextView = TextView(this).apply {
            text = "Min:"
        }
        val maxTextView = TextView(this).apply {
            text = "Max:"
        }

        // Crear los TextViews para mostrar los valores de Min y Max
        val minValueTextView = TextView(this).apply {
            text = "0"
        }
        val maxValueTextView = TextView(this).apply {
            text = "10"
        }

        // Crear el SeekBar
        val seekBar = SeekBar(this).apply {
            max = 10
            progress = 3
        }

        // Crear el botón de enviar
        val sendButton = Button(this).apply {
            text = "Enviar"
            gravity = Gravity.CENTER
        }

        // Crear el ImageView
        val imageView = ImageView(this).apply {
            setImageResource(R.drawable.like) // Actualiza con la ruta de tu imagen
            visibility = View.VISIBLE
            layoutParams = LinearLayout.LayoutParams(200, 200) // Tamaño fijo de la imagen
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

        // Función para agregar un número a la imagen
        fun addNumberToImage(originalDrawable: Int, number: Int): BitmapDrawable {
            val originalBitmap = (ContextCompat.getDrawable(this, originalDrawable) as BitmapDrawable).bitmap
            val bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)
            val canvas = Canvas(bitmap)
            val paint = Paint().apply {
                color = Color.RED
                textSize = 40f
                isAntiAlias = true
            }
            val text = number.toString()
            val x = (bitmap.width * 0.5 - paint.measureText(text) * 0.5).toFloat()
            val y = (bitmap.height * 0.5 + paint.textSize * 0.5).toFloat()
            canvas.drawText(text, x, y, paint)
            return BitmapDrawable(resources, bitmap)
        }

        // Acción del SeekBar para cambiar el número en la imagen
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Actualizar la imagen con el número
                imageView.setImageDrawable(addNumberToImage(R.drawable.like, progress))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // No se necesita implementar
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // No se necesita implementar
            }
        })

        // Agregar las vistas al layout
        layout.addView(textView)
        layout.addView(button)

        // Añadir las vistas para Min y Max
        val minMaxLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
        }
        minMaxLayout.addView(minTextView)
        minMaxLayout.addView(minValueTextView)
        minMaxLayout.addView(maxTextView)
        minMaxLayout.addView(maxValueTextView)

        layout.addView(minMaxLayout)
        layout.addView(seekBar)
        layout.addView(sendButton)
        layout.addView(imageView)

        // Establecer el layout como la vista principal de la actividad
        setContentView(layout)
    }
}
