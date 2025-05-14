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
    // === ESTADOS PRINCIPALES ===
    var input by remember { mutableStateOf("") } // Entrada del usuario (número base)
    var errorMsg by remember { mutableStateOf("") } // Mensaje de error a mostrar
    var tabla by remember { mutableStateOf<List<String>>(emptyList()) } // Lista que contiene la tabla generada

    // === CONFIGURACIONES DE TABLA ===
    var mostrarTabla by remember { mutableStateOf(true) } // Controla si se muestra la tabla
    var seleccionMultiplicacion by remember { mutableStateOf("Tabla completa") } // Opción seleccionada
    val opciones = listOf("Tabla completa", "Solo pares", "Solo impares") // Opciones disponibles

    var checkConfirmacion by remember { mutableStateOf(false) } // Confirmación con CheckBox

    MaterialTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // === TÍTULO PRINCIPAL ===
            Text("Ejercicio 2: Tabla de Multiplicar", style = MaterialTheme.typography.h5)

            // === CAMPO DE ENTRADA NUMÉRICA ===
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

            // === SELECCIÓN DE TIPO DE TABLA (SIMULA COMBOBOX CON RADIOGROUP) ===
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

            // === CHECKBOX DE CONFIRMACIÓN ANTES DE GENERAR ===
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Checkbox(
                    checked = checkConfirmacion,
                    onCheckedChange = { checkConfirmacion = it }
                )
                Text("Confirmar para generar tabla")
            }

            // === BOTÓN PARA MOSTRAR LA TABLA DE MULTIPLICAR ===
            Button(
                onClick = {
                    val numero = input.toIntOrNull()
                    if (numero == null) {
                        // Validación: entrada no numérica
                        errorMsg = "Por favor ingresa un número válido"
                        tabla = emptyList()
                    } else if (numero !in 1..12) {
                        // Validación: número fuera de rango
                        errorMsg = "El número debe estar entre 1 y 12"
                        tabla = emptyList()
                    } else if (!checkConfirmacion) {
                        // Validación: falta confirmación del usuario
                        errorMsg = "Debes confirmar antes de continuar"
                        tabla = emptyList()
                    } else {
                        // Generar la tabla según la selección
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

            // === MENSAJE DE ERROR SI CORRESPONDE ===
            if (errorMsg.isNotEmpty()) {
                Text(errorMsg, color = MaterialTheme.colors.error)
            }

            // === MUESTRA LA TABLA SI EXISTE Y ESTÁ PERMITIDO ===
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

// === FUNCIÓN PRINCIPAL ===
// Punto de entrada de la aplicación de escritorio
fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Tabla de Multiplicar") {
        App()
    }
}
