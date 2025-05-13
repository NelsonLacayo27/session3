package com.example.adivinaelnumero

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlin.random.Random


@Composable
@Preview
fun App() {
    // === 1. Estados principales ===
    var rangoSeleccionado by remember { mutableStateOf(10) }
    var numeroSecreto by remember { mutableStateOf(Random.nextInt(1, rangoSeleccionado + 1)) }
    var input by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    // === 2. Controles adicionales ===
    val rangos = listOf(10, 20, 50)
    var expanded by remember { mutableStateOf(false) }
    var mostrarPista by remember { mutableStateOf(false) }
    val temas = listOf("Claro", "Oscuro")
    var temaSeleccionado by remember { mutableStateOf(temas[0]) }

    // === 3. Tema dinámico y diseño ===
    val colors = if (temaSeleccionado == "Claro") lightColors() else darkColors()
    MaterialTheme(colors = colors) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Título
            Text(
                text = "Adivina un número entre 1 y $rangoSeleccionado",
                style = MaterialTheme.typography.h6
            )

            // ComboBox (DropdownMenu) para seleccionar rango
            Box {
                Button(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
                    Text("Rango: 1–$rangoSeleccionado")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    rangos.forEach { r ->
                        DropdownMenuItem(onClick = {
                            rangoSeleccionado = r
                            numeroSecreto = Random.nextInt(1, r + 1)
                            input = ""
                            resultado = ""
                            expanded = false
                        }) {
                            Text("1 – $r")
                        }
                    }
                }
            }

            // CheckBox para pistas alto/bajo
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = mostrarPista,
                    onCheckedChange = { mostrarPista = it }
                )
                Spacer(Modifier.width(8.dp))
                Text("Mostrar pista (alto/bajo)")
            }

            // RadioGroup para tema
            Text("Tema:")
            temas.forEach { t ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (t == temaSeleccionado),
                            onClick = { temaSeleccionado = t }
                        )
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (t == temaSeleccionado),
                        onClick = { temaSeleccionado = t }
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(t)
                }
            }

            // === 4. Input y comprobación ===
            OutlinedTextField(
                value = input,
                onValueChange = { input = it; resultado = "" },
                label = { Text("Tu intento") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val intento = input.toIntOrNull()
                    resultado = when {
                        intento == null -> "Por favor ingresa un número válido"
                        intento == numeroSecreto -> "¡Correcto!"
                        mostrarPista && intento < numeroSecreto -> "Demasiado bajo"
                        mostrarPista && intento > numeroSecreto -> "Demasiado alto"
                        else -> "Intenta de nuevo"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Comprobar")
            }

            // Mostrar resultado
            Text(resultado, style = MaterialTheme.typography.body1)

            // === 5. Reiniciar juego ===
            Button(
                onClick = {
                    numeroSecreto = Random.nextInt(1, rangoSeleccionado + 1)
                    input = ""
                    resultado = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Reiniciar juego")
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Adivina el Número") {
        App()
    }
}
