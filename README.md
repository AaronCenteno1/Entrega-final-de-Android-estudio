# Entrega-final-de-Android-estudio
🎮 RegistroPokemon - App de Gestión de Usuarios Pokémon

Este proyecto es una aplicación Android desarrollada en Java que permite gestionar usuarios mediante SQLite de forma local.
Los usuarios pueden registrarse, iniciar sesión y guardar información personal como nombre, apellido, edad, fecha de cumpleaños y una foto de perfil.

Además, la aplicación integra la API de Pokémon, permitiendo que el usuario busque información de cualquier Pokémon directamente desde la app.

Está pensado como práctica de desarrollo Android con manejo de CRUD, autenticación básica y consumo de API externa.

✒️ Autor

Aaron Centeno Esquivel
📧 Correo: a.centeno2@alumnos.santotomas.cl

💼 GitHub: AaronCenteno1

🛠️ Entorno de Desarrollo

🧩 Entorno: Android Studio (JetBrains s.r.o.)

☕ Lenguaje: Java 17

💾 Base de Datos: SQLite nativa de Android

🧱 SDK Mínimo: API 24 (Android 7.0 Nougat)

🎯 SDK Objetivo: API 33 (Android 13) o superior

⚙️ JDK: Versión 21.0.6 (OpenJDK 21, 64-bit Server VM)

🚀 Cómo Funciona la Aplicación

El flujo general de la aplicación es simple e intuitivo:

1. LoginActivity

Pantalla de inicio donde el usuario puede iniciar sesión con su cuenta existente.

Incluye un botón que permite crear una nueva cuenta, redirigiendo al RegistroActivity.

2. RegistroActivity

Permite crear un nuevo usuario ingresando sus datos básicos.

Al completar el registro, el usuario puede iniciar sesión con la cuenta creada.

3. ActivityUsuario

Pantalla principal tras iniciar sesión.

Permite al usuario rellenar o actualizar su información personal: nombre, apellido, edad, fecha de cumpleaños y foto de perfil.

Incluye un botón Buscar Pokémon, que consume la API de Pokémon:

El usuario ingresa el nombre de un Pokémon.

La app muestra información detallada del Pokémon y su imagen.

Botón Guardar datos para almacenar la información del usuario en SQLite.

🏗️ Arquitectura y Clases Principales

La aplicación sigue una arquitectura sencilla separando la lógica de datos de la interfaz.

📂 Directorio de base de datos

DbHelper.java (El “Ayudante”)
Extiende SQLiteOpenHelper y gestiona la creación y actualización de la base de datos.

onCreate(): Crea la tabla de usuarios al iniciar la app.

onUpgrade(): Permite actualizar el esquema si cambia la versión.

🌐 Integración con API Pokémon

Método que realiza la consulta HTTP a la API de Pokémon.

Obtiene información como nombre, tipo, habilidades y foto.

Permite mostrar la información dentro de la app de forma interactiva.
