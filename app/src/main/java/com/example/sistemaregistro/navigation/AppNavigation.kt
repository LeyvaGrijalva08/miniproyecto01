package com.example.sistemaregistro.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sistemaregistro.ui.DetailScreen
import com.example.sistemaregistro.ui.FormScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "form_screen") {
        // Pantalla de Formulario
        composable("form_screen") {
            FormScreen(onNavigateToDetail = { matricula, nombre, carrera, turno, estatus ->
                navController.navigate("detail_screen/$matricula/$nombre/$carrera/$turno/$estatus")
            })
        }

        // Pantalla de Detalle recibiendo argumentos
        composable(
            route = "detail_screen/{matricula}/{nombre}/{carrera}/{turno}/{estatus}",
            arguments = listOf(
                navArgument("matricula") { type = NavType.StringType },
                navArgument("nombre") { type = NavType.StringType },
                navArgument("carrera") { type = NavType.StringType },
                navArgument("turno") { type = NavType.StringType },
                navArgument("estatus") { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            val matricula = backStackEntry.arguments?.getString("matricula") ?: ""
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val carrera = backStackEntry.arguments?.getString("carrera") ?: ""
            val turno = backStackEntry.arguments?.getString("turno") ?: ""
            val estatus = backStackEntry.arguments?.getBoolean("estatus") ?: true

            DetailScreen(matricula, nombre, carrera, turno, estatus) {
                navController.popBackStack()
            }
        }
    }
}