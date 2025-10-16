package com.example.alertabodega

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alertabodega.ui.theme.AlertaBodegaTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlertaBodegaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigator(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AppNavigator(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var alarmActive by remember { mutableStateOf(false) }

    NavHost(navController = navController, startDestination = "selection", modifier = modifier) {
        composable("selection") {
            SelectionScreen(navController = navController)
        }
        composable("client") {
            ClientScreen(
                navController = navController,
                isAlarmActive = alarmActive,
                onMotionDetected = { alarmActive = true }
            )
        }
        composable("dashboard") {
            DashboardScreen(
                navController = navController,
                isAlarmActive = alarmActive,
                onResetAlarm = { alarmActive = false }
            )
        }
    }
}

@Composable
fun SelectionScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Seleccione el rol:", fontSize = 22.sp)
        Button(onClick = { navController.navigate("client") }) {
            Text("Sensor de Movimiento")
        }
        Button(onClick = { navController.navigate("dashboard") }) {
            Text("Dashboard (Receptor de Alarma)")
        }
    }
}

@Composable
fun ClientScreen(navController: NavController, isAlarmActive: Boolean, onMotionDetected: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Aplicación Cliente (Sensor)")
        Button(onClick = onMotionDetected, enabled = !isAlarmActive) {
            Text("Simular Detección de Movimiento")
        }
        if (isAlarmActive) {
            Text("Alarma enviada. Esperando restablecimiento.")
        }
        Button(onClick = { navController.popBackStack() }) {
            Text("Volver")
        }
    }
}

@Composable
fun DashboardScreen(navController: NavController, isAlarmActive: Boolean, onResetAlarm: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Aplicación Dashboard (Control)")
        if (isAlarmActive) {
            Text("¡ALARMA ACTIVADA!", color = Color.Red, fontSize = 24.sp)
            Button(onClick = onResetAlarm) {
                Text("Restablecer Alarma")
            }
        } else {
            Text("Todo tranquilo")
        }
        Button(onClick = { navController.popBackStack() }) {
            Text("Volver")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AlertaBodegaTheme {
        val navController = rememberNavController()
        SelectionScreen(navController)
    }
}
