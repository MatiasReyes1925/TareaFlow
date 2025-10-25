# TareaFlow

TareaFlow es una aplicación móvil desarrollada en Android utilizando Kotlin y Jetpack Compose. Su propósito es permitir a los usuarios gestionar tareas personales de forma eficiente, visual y trazable. La aplicación está diseñada con una arquitectura modular, validación en hardware real y flujos de navegación claros, facilitando tanto el uso como la defensa técnica del proyecto.

## Descripción general

La aplicación permite a los usuarios:

- Iniciar sesión y registrar nuevos usuarios
- Visualizar tareas pendientes y completadas
- Agregar, editar y eliminar tareas
- Marcar tareas como completadas
- Navegar entre pantallas de forma fluida
- Validar datos en tiempo real para evitar cierres inesperados

El proyecto se enfoca en la trazabilidad de cada acción, la limpieza arquitectónica y la preparación para auditoría técnica.

## Integrantes del equipo

- Matías Reyes 
  Encargado de la integración de funcionalidades, validación en hardware real, corrección de errores críticos y documentación técnica.  
  Especialista en trazabilidad, modularidad y defensa conceptual de cada componente.

## Funcionalidades implementadas

- Inicio de sesión con validación de credenciales
- Registro de nuevos usuarios
- Visualización de tareas pendientes y completadas
- Agregado, edición y eliminación de tareas
- Marcado de tareas como completadas
- Navegación entre pantallas con NavController
- Validación de datos nulos para evitar cierres inesperados
- Arquitectura modular con separación por capas (UI, ViewModel, Repository, Model, Database)
- Documentación técnica alineada con estándares de defensa oral
- Validación del flujo completo en hardware real

## Requisitos previos

- Android Studio actualizado
- Kotlin 1.9 o superior
- Emulador o dispositivo físico con Android 8.0 o superior
- Conexión a internet para sincronizar dependencias

## Pasos para ejecutar el proyecto

1. Clonar el repositorio:

git clone https://github.com/MatiasReyes1925/TareaFlow.git

Código

2. Abrir el proyecto en Android Studio.

3. Sincronizar el proyecto con Gradle.

4. Configurar un emulador o conectar un dispositivo físico.

5. Ejecutar la aplicación desde el botón "Run" o el menú `Run > Run 'app'`.

6. Iniciar sesión con un usuario válido o registrar uno nuevo.

## Notas técnicas

- Los ViewModels se instancian manualmente en `MainActivity` utilizando `remember`, evitando el uso de `ViewModelFactory` para mantener la arquitectura simple y funcional.
- La carga de tareas se encapsula en `LaunchedEffect(usuarioActual?.id)` para evitar ejecuciones prematuras.
- La pantalla de inicio (`PantallaInicio`) valida que `usuarioActual` no sea nulo antes de renderizar la interfaz, mostrando un `CircularProgressIndicator` si es necesario.
- Cada ajuste fue probado en hardware real y documentado para trazabilidad técnica.

## Distribución de archivos

```text
├── manifests/
│   └── AndroidManifest.xml

├── java/com.example.tareaflow/
│
│   ├── model/
│   │   ├── Tarea.kt
│   │   ├── Usuario.kt
│   │   ├── TareaDao.kt
│   │   └── UsuarioDao.kt
│
│   ├── database/
│   │   └── AppDataBase.kt
│
│   ├── repository/
│   │   ├── TareaRepository.kt
│   │   ├── UsuarioRepository.kt
│   │   └── UsuarioFormRepository.kt
│
│   ├── ui/
│   │   ├── MainActivity.kt
│   │   ├── PantallaInicio.kt
│   │   ├── PantallaPrincipal.kt
│   │   ├── PantallaRegistro.kt
│   │   ├── AgregarTarea.kt
│   │   ├── EditarTarea.kt
│   │   ├── InicioSesion.kt
│   │   ├── Registro.kt
│   │   ├── Navegacion.kt
│   │   └── Camara.kt
│
│   └── viewmodel/
│       ├── TareaViewModel.kt
│       ├── UsuarioViewModel.kt
│       └── FormUsuarioViewModel.kt

├── res/
│   └── xml/
│       └── file_paths.xml

├── Gradle Scripts/
│   ├── build.gradle.kts (Project: TareaFlow)
│   ├── build.gradle.kts (Module: app)
│   ├── proguard-rules.pro
│   ├── gradle.properties
│   ├── gradle-wrapper.properties
│   ├── libs.versions.toml
│   ├── local.properties
│   └── settings.gradle.kts
```

