package com.example.tablamultiplicar

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

/**
 * Descripción:
 *   Crear una aplicación que muestre la tabla de multiplicar del 1 al 12
 *   de un número ingresado por el usuario.
 *
 * Requisitos:
 *   ü Incluir un TextField para ingresar el número base (por ejemplo, 3).
 *   ü Al presionar un Button, mostrar la tabla de multiplicar del número desde 1 hasta 12.
 *   ü Usar una Column para presentar cada línea de la tabla (por ejemplo, “3 x 1 = 3”).
 *   ü Validar que se ingrese un número y manejar posibles errores.
 *   ü Aplicar estilo con MaterialTheme y Modifier.padding para mantener una interfaz ordenada y legible.
 *
 * Estado de la entrega:
 *   Grupo: IICorte-3
 *   Estado de la entrega: No se ha enviado nada en esta tarea
 *   Estado de la calificación: Sin calificar
 *   Tiempo restante: 3 días 4 horas restante
 *   Última modificación: -
 *   Comentarios de la entrega: (0)
 *
 * Criterios de calificación:
 *   1. Funcionamiento general de la aplicación (6 pts)
 *      – La aplicación compila sin errores y realiza correctamente las funciones requeridas.
 *   2. Uso correcto de controles básicos (8 pts)
 *      – Incluye y utiliza adecuadamente TextField, Text, Button.
 *   3. Manejo del estado con Compose (6 pts)
 *      – Uso correcto de remember, mutableStateOf y actualización de interfaz según el estado.
 *   4. Aplicación de Material UI y diseño visual (6 pts)
 *      – Uso apropiado de MaterialTheme, disposición visual clara, espaciado, y adaptación a pantalla.
 *   5. Documentación y limpieza del código (4 pts)
 *      – Código legible, organizado y comentado adecuadamente. Buenas prácticas de programación.
 */

@Composable
@Preview
fun App() {
    // Estado para el número base ingresado y posible mensaje de error
    var input by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }
    // Estado para la lista de líneas de la tabla a mostrar
    var tabla by remember { mutableStateOf<List<String>>(emptyList()) }

    MaterialTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Título
            Text("Tabla de multiplicar (1–12)", style = MaterialTheme.typography.h6)

            // TextField para ingresar número base
            OutlinedTextField(
                value = input,
                onValueChange = {
                    input = it
                    errorMsg = ""
                },
                label = { Text("Número base (1–12)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Botón para generar la tabla
            Button(
                onClick = {
                    val numero = input.toIntOrNull()
                    when {
                        numero == null -> {
                            tabla = emptyList()
                            errorMsg = "Por favor ingresa un número válido"
                        }
                        numero !in 1..12 -> {
                            tabla = emptyList()
                            errorMsg = "El número debe estar entre 1 y 12"
                        }
                        else -> {
                            errorMsg = ""
                            tabla = (1..12).map { i ->
                                "$numero x $i = ${numero * i}"
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mostrar tabla")
            }

            // Mensaje de error (si lo hay)
            if (errorMsg.isNotEmpty()) {
                Text(errorMsg, color = MaterialTheme.colors.error)
            }

            // Mostrar cada línea de la tabla
            Column(modifier = Modifier.fillMaxWidth()) {
                tabla.forEach { linea ->
                    Text(linea, modifier = Modifier.padding(vertical = 2.dp))
                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Tabla de Multiplicar") {
        App()
    }
}
