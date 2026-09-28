package com.example.sistemaregistro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.sistemaregistro.navigation.AppNavigation
import com.example.sistemaregistro.ui.theme.SistemaregistroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SistemaregistroTheme {
                AppNavigation() // Inicializa el gestor de navegacion principal
            }
        }
    }
}