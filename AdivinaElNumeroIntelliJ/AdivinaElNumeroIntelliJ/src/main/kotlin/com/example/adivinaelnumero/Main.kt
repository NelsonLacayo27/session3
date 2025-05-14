package com.example.adivinaelnumero

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlin.random.Random

@Composable
@Preview
fun App() {
    // === ESTADOS PRINCIPALES ===
    var numeroSecreto by remember { mutableStateOf(Random.nextInt(1, 11)) }
    var input by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    // === CONFIGURACIONES ===
    val rangos = listOf(10, 20, 50)
    var rangoSeleccionado by remember { mutableStateOf(rangos[0]) }
    var mostrarPista by remember { mutableStateOf(false) }
    val temas = listOf("Claro", "Oscuro")
    var temaSeleccionado by remember { mutableStateOf(temas[0]) }
    var expanded by remember { mutableStateOf(false) }

    // === TEMA DINÁMICO ===
    val colores = if (temaSeleccionado == "Claro") lightColors() else darkColors()

    MaterialTheme(colors = colores) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // === TÍTULO ===
            Text("🎯 Adivina el número secreto", style = MaterialTheme.typography.h5)

            // === CAMPO DE ENTRADA ===
            OutlinedTextField(
                value = input,
                onValueChange = {
                    input = it
                    resultado = ""
                    val intento = it.toIntOrNull()
                    if (intento != null && (intento !in 1..10)) {
                        resultado = "⚠️ Solo puedes ingresar números del 1 al 10"
                    }
                },
                label = { Text("Número del 1 al 10") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // === BOTÓN COMPROBAR ===
            Button(
                onClick = {
                    val intento = input.toIntOrNull()
                    resultado = when {
                        intento == null -> "⚠️ Ingresa un número válido"
                        intento !in 1..10 -> "⚠️ Solo puedes ingresar números del 1 al 10"
                        intento == numeroSecreto -> "🎉 ¡Correcto! El número era $numeroSecreto"
                        mostrarPista && intento < numeroSecreto -> "🔻 Demasiado bajo"
                        mostrarPista && intento > numeroSecreto -> "🔺 Demasiado alto"
                        else -> "❌ Intenta de nuevo"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("✅ Comprobar")
            }

            // === RESULTADO ===
            if (resultado.isNotEmpty()) {
                Text(resultado, fontSize = 18.sp)
            }

            // === BOTÓN REINICIAR ===
            Button(
                onClick = {
                    numeroSecreto = Random.nextInt(1, 11)
                    input = ""
                    resultado = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("🔄 Reiniciar juego")
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // === CONFIGURACIONES ===
            Text("⚙️ Configuración del juego", fontSize = 16.sp)

            // RANGO
            Text("🎚️ Rango disponible:")
            Box {
                Button(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
                    Text("1 – $rangoSeleccionado")
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    rangos.forEach { r ->
                        DropdownMenuItem(onClick = {
                            rangoSeleccionado = r
                            expanded = false
                        }) {
                            Text("1 – $r")
                        }
                    }
                }
            }

            // CHECKBOX DE PISTA
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = mostrarPista,
                    onCheckedChange = { mostrarPista = it }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("💡 Mostrar pista (alto o bajo)")
            }

            // TEMA VISUAL
            Text("🎨 Tema visual:")
            temas.forEach { tema ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (tema == temaSeleccionado),
                            onClick = { temaSeleccionado = tema }
                        )
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (tema == temaSeleccionado),
                        onClick = { temaSeleccionado = tema }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(tema)
                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "🎯 Adivina el número") {
        App()
    }
}

}
