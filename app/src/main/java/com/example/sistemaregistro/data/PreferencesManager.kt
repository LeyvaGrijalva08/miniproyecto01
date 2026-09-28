package com.example.sistemaregistro.data

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    // Archivo XML interno donde Android guarda las preferencias
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("StudentPreferences", Context.MODE_PRIVATE)

    companion object {
        const val KEY_MATRICULA = "key_matricula"
        const val KEY_NOMBRE = "key_nombre"
        const val KEY_CARRERA = "key_carrera"
        const val KEY_TURNO = "key_turno"
        const val KEY_ESTATUS = "key_estatus"
    }

    // Métodos de guardado
    fun saveStudentData(matricula: String, nombre: String, carrera: String, turno: String, estatus: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_MATRICULA, matricula)
        editor.putString(KEY_NOMBRE, nombre)
        editor.putString(KEY_CARRERA, carrera)
        editor.putString(KEY_TURNO, turno)
        editor.putBoolean(KEY_ESTATUS, estatus)
        editor.apply() // Guarda
    }

    // Métodos de consulta (Lectura)[cite: 10]
    fun getMatricula(): String = sharedPreferences.getString(KEY_MATRICULA, "") ?: ""
    fun getNombre(): String = sharedPreferences.getString(KEY_NOMBRE, "") ?: ""
    fun getCarrera(): String = sharedPreferences.getString(KEY_CARRERA, "Ingeniería de Software") ?: "Ingeniería de Software"
    fun getTurno(): String = sharedPreferences.getString(KEY_TURNO, "Matutino") ?: "Matutino"
    fun getEstatus(): Boolean = sharedPreferences.getBoolean(KEY_ESTATUS, true)
}