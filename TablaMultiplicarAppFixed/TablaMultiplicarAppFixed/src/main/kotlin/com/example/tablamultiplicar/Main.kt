package com.example.tablamultiplicar

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {
    // Estados principales
    var input by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }
    var tabla by remember { mutableStateOf<List<String>>(emptyList()) }

    // Estados adicionales para criterios de calificación
    var mostrarTabla by remember { mutableStateOf(true) }
    var seleccionMultiplicacion by remember { mutableStateOf("Tabla completa") }
    val opciones = listOf("Tabla completa", "Solo pares", "Solo impares")

    var checkConfirmacion by remember { mutableStateOf(false) }

    MaterialTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Ejercicio 2: Tabla de Multiplicar", style = MaterialTheme.typography.h5)

            // Campo de entrada numérica
            OutlinedTextField(
                value = input,
                onValueChange = {
                    input = it
                    errorMsg = ""
                },
                label = { Text("Número base (1–12)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // ComboBox (Dropdown) para tipo de tabla
            Text("Selecciona el tipo de tabla:")
            opciones.forEach { opcion ->
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    RadioButton(
                        selected = seleccionMultiplicacion == opcion,
                        onClick = { seleccionMultiplicacion = opcion }
                    )
                    Text(opcion)
                }
            }

            // CheckBox adicional (opcional)
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Checkbox(
                    checked = checkConfirmacion,
                    onCheckedChange = { checkConfirmacion = it }
                )
                Text("Confirmar para generar tabla")
            }

            // Botón para mostrar tabla
            Button(
                onClick = {
                    val numero = input.toIntOrNull()
                    if (numero == null) {
                        errorMsg = "Por favor ingresa un número válido"
                        tabla = emptyList()
                    } else if (numero !in 1..12) {
                        errorMsg = "El número debe estar entre 1 y 12"
                        tabla = emptyList()
                    } else if (!checkConfirmacion) {
                        errorMsg = "Debes confirmar antes de continuar"
                        tabla = emptyList()
                    } else {
                        errorMsg = ""
                        tabla = (1..12)
                            .filter {
                                when (seleccionMultiplicacion) {
                                    "Solo pares" -> it % 2 == 0
                                    "Solo impares" -> it % 2 != 0
                                    else -> true
                                }
                            }
                            .map { i -> "$numero x $i = ${numero * i}" }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mostrar tabla")
            }

            // Mensaje de error
            if (errorMsg.isNotEmpty()) {
                Text(errorMsg, color = MaterialTheme.colors.error)
            }

            // Mostrar tabla
            if (mostrarTabla && tabla.isNotEmpty()) {
                Divider(thickness = 1.dp)
                Column {
                    tabla.forEach {
                        Text(it, modifier = Modifier.padding(4.dp))
                    }
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
