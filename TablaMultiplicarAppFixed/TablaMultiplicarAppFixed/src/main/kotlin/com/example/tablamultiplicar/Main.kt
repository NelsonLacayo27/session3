package com.example.tablamultiplicar

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application


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
