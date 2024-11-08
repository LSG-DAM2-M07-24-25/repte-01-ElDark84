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

        // Crear un FrameLayout principal para organizar las vistas
        val mainLayout = FrameLayout(this).apply {
            setPadding(16, 16, 16, 16) // Opcional, para dar algo de espacio
        }

        // Crear un LinearLayout para el título y el botón
        val topLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        // Crear el TextView con el título "Repte 01"
        val textView = TextView(this).apply {
            text = "Repte 01"
            textSize = 36f // tamaño del texto grande
            setTextColor(Color.BLUE) // color azul
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER // Centrar el texto
        }

        // Crear el botón que activará el menú desplegable en la parte superior
        val button = Button(this).apply {
            text = "Tria un Icon"
            gravity = Gravity.CENTER
        }

        // Crear un LinearLayout secundario para centrar los demás elementos
        val secondaryLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER // Centrar horizontalmente todos los elementos
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER // Centrar verticalmente el layout
            )
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

        // Crear el RelativeLayout para la imagen y el número
        val relativeLayout = RelativeLayout(this)

        // Crear el ImageView
        val imageView = ImageView(this).apply {
            setImageResource(R.drawable.like) // Actualiza con la ruta de tu imagen
            visibility = View.VISIBLE
            layoutParams = RelativeLayout.LayoutParams(200, 200) // Tamaño fijo de la imagen
        }

        // Crear el TextView para el número
        val numberTextView = TextView(this).apply {
            textSize = 24f
            setTextColor(Color.RED)
            id = View.generateViewId()
        }

        // Configurar el layout params del ImageView y el TextView
        val imageViewParams = RelativeLayout.LayoutParams(200, 200).apply {
            addRule(RelativeLayout.CENTER_IN_PARENT)
        }
        val numberTextViewParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            addRule(RelativeLayout.RIGHT_OF, imageView.id)
            addRule(RelativeLayout.ALIGN_BASELINE, imageView.id)
            marginStart = 16
        }

        // Añadir las vistas al RelativeLayout
        relativeLayout.addView(imageView, imageViewParams)
        relativeLayout.addView(numberTextView, numberTextViewParams)

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

        // Acción del SeekBar para cambiar el número al lado de la imagen
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Actualizar el número al lado de la imagen
                numberTextView.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // No se necesita implementar
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // No se necesita implementar
            }
        })

        // Agregar el TextView y el botón al topLayout
        topLayout.addView(textView)
        topLayout.addView(button)

        // Añadir las vistas para Min y Max
        val minMaxLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
        }
        minMaxLayout.addView(minTextView)
        minMaxLayout.addView(minValueTextView)
        minMaxLayout.addView(maxTextView)
        minMaxLayout.addView(maxValueTextView)

        // Agregar las vistas al secondaryLayout
        secondaryLayout.addView(minMaxLayout)
        secondaryLayout.addView(seekBar)
        secondaryLayout.addView(sendButton)
        secondaryLayout.addView(relativeLayout)

        // Añadir topLayout y secondaryLayout al mainLayout
        mainLayout.addView(topLayout)
        mainLayout.addView(secondaryLayout)

        // Establecer el mainLayout como la vista principal de la actividad
        setContentView(mainLayout)
    }
}
