package com.example.sistemaregistro.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.sistemaregistro.data.PreferencesManager

@Composable
fun FormScreen(onNavigateToDetail: (String, String, String, String, Boolean) -> Unit) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    var matricula by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var carrera by remember { mutableStateOf("Ingeniería de Software") }
    var turno by remember { mutableStateOf("Matutino") }
    var estatus by remember { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Registro de Estudiantes", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = matricula,
            onValueChange = { matricula = it },
            label = { Text("Matrícula") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre Completo") },
            modifier = Modifier.fillMaxWidth()
        )

        // DropdownMenu
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = carrera,
                onValueChange = {},
                readOnly = true,
                label = { Text("Carrera") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listOf("Ingeniería de Software", "Ingenieria Civil", "Ingenieria en Geodesia").forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            carrera = opcion
                            expanded = false
                        }
                    )
                }
            }
        }

        // RadioButton
        Text("Turno:")
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = (turno == "Matutino"),
                onClick = { turno = "Matutino" }
            )
            Text("Matutino", modifier = Modifier.clickable { turno = "Matutino" })
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = (turno == "Vespertino"),
                onClick = { turno = "Vespertino" }
            )
            Text("Vespertino", modifier = Modifier.clickable { turno = "Vespertino" })
        }

        // Switch
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Estatus Activo:")
            Switch(
                checked = estatus,
                onCheckedChange = { estatus = it }
            )
        }

        Spacer(modifier = Modifier.weight(1f)) // Empuja los botones hacia abajo

        // Guardar datos
        Button(
            onClick = {
                if (matricula.isNotBlank() && nombre.isNotBlank()) {
                    // Guardamos el valor actual en variables temporales inmutables
                    val matriculaGuardar = matricula
                    val nombreGuardar = nombre

                    // Guardamos en SharedPreferences usando los datos capturados
                    preferencesManager.saveStudentData(matriculaGuardar, nombreGuardar, carrera, turno, estatus)
                    Toast.makeText(context, "Datos guardados", Toast.LENGTH_SHORT).show()

                    // Ahora si podemos limpiar las cajas de texto de la pantalla con seguridad
                    matricula = ""
                    nombre = ""

                    // Navegamos usando las variables temporales que si tienen informacion
                    onNavigateToDetail(matriculaGuardar, nombreGuardar, carrera, turno, estatus)
                } else {
                    Toast.makeText(context, "Llene los campos obligatorios", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar")
        }

        // Mostrar ultimo registro (Lectura de SharedPreferences)
        Button(
            onClick = {

                matricula = preferencesManager.getMatricula()
                nombre = preferencesManager.getNombre()
                carrera = preferencesManager.getCarrera()
                turno = preferencesManager.getTurno()
                estatus = preferencesManager.getEstatus()

                Toast.makeText(context, "Último registro cargado", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth(),

            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text("Mostrar último registro")
        }
    }
}