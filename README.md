# 🚨 Alerta Bodega (Simulación Local)

**AlertaBodega** es una aplicación de demostración para Android que simula un sistema de seguridad simple.  
A diferencia de un sistema IoT real que requiere conexión de red, esta versión está **totalmente contenida en un solo dispositivo**, diseñada para demostrar la lógica de la aplicación, la gestión de estado y la navegación usando **Jetpack Compose**.

---

## 📜 Descripción del Proyecto

La aplicación permite al usuario alternar entre dos roles simulados:

- **Sensor de Movimiento:** Pantalla con un botón para simular la detección de una intrusión.  
- **Dashboard de Monitoreo:** Pantalla que muestra el estado actual del sistema y activa una alerta visual cuando el sensor “detecta” algo.

El propósito del proyecto es ilustrar cómo se puede **gestionar un estado compartido (`alarmActive`)** entre diferentes pantallas (Composables) utilizando un **NavHost** de Jetpack Navigation, aplicando el patrón de **elevación de estado (state hoisting)**.

---

## 🚀 Características Principales

- 🧩 **100% Kotlin + Jetpack Compose:** Interfaz moderna y declarativa.  
- 🧭 **Navegación Simple:** Uso de `NavHost` y `NavController` para gestionar el flujo entre pantallas.  
- 🔄 **Gestión de Estado Centralizada:** El estado de la alarma se eleva al componente `AppNavigator` y se pasa a las pantallas hijas, asegurando que la UI refleje el estado real.  
- 📱 **Simulación Autocontenida:** No requiere permisos de red, hardware externo ni configuraciones adicionales. Funciona directamente al ejecutar.

---

## 🛠️ Tecnologías y Librerías Utilizadas

- **Kotlin:** Lenguaje de programación principal.  
- **Jetpack Compose:** Toolkit de UI declarativo para Android.  
- **Material 3:** Componentes visuales (Button, Text, Scaffold, etc.).  
- **Navigation Compose:** Para la navegación entre pantallas.

---

## 📸 Capturas de Pantalla

| Pantalla de Selección | Sensor (Antes de la Alarma) | Dashboard (Alarma Activada) |
|:----------------------:|:---------------------------:|:----------------------------:|
| <img width="250" alt="Pantalla de Selección" src="https://github.com/user-attachments/assets/7b73e59c-2b3b-40f7-b354-92bd451e2c58" /> | <img width="250" alt="Sensor Antes de la Alarma" src="https://github.com/user-attachments/assets/64af0094-1af8-4e64-9119-8eef5734f0a0" /> | <img width="250" alt="Dashboard Alarma Activada" src="https://github.com/user-attachments/assets/de393020-3a96-446a-b24b-aedfa4a72f8f" /> |


---

## 🕹️ Cómo Funciona el Código

La lógica principal se encuentra en el composable **`AppNavigator`** dentro de `MainActivity.kt`.  
Este actúa como **controlador de navegación y propietario del estado de la alarma.**

```kotlin
@Composable
fun AppNavigator(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    // 1. El estado de la alarma se "eleva" a este nivel superior (Hoisting State)
    var alarmActive by remember { mutableStateOf(false) }

    NavHost(navController = navController, startDestination = "selection", modifier = modifier) {
        composable("selection") {
            SelectionScreen(navController = navController)
        }
        composable("client") {
            // 2. La pantalla del Sensor recibe una función para CAMBIAR el estado
            ClientScreen(
                navController = navController,
                isAlarmActive = alarmActive,
                onMotionDetected = { alarmActive = true }
            )
        }
        composable("dashboard") {
            // 3. La pantalla del Dashboard LEE el estado y puede restablecerlo
            DashboardScreen(
                navController = navController,
                isAlarmActive = alarmActive,
                onResetAlarm = { alarmActive = false }
            )
        }
    }
}

## 🏃‍♂️ Cómo Probar la Aplicación

Sigue estos pasos para ejecutar la simulación local:

```bash
# 1️⃣ Clona este repositorio
git clone https://github.com/AxelvillaInacap/AlarmaBodega

# 2️⃣ Abre el proyecto con Android Studio
# (Se recomienda usar la versión Giraffe o superior)

# 3️⃣ Ejecuta la aplicación
# Puedes usar un emulador o un dispositivo físico (botón ▶️)

# 4️⃣ Desde la pantalla de selección
# Navega a "Sensor de Movimiento"

# 5️⃣ Pulsa "Simular Detección de Movimiento"
# Esto activará la alarma

# 6️⃣ Regresa a la pantalla "Dashboard"
# Verás la alerta activada

# 7️⃣ Presiona "Restablecer Alarma"
# La aplicación volverá al estado normal
