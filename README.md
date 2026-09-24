# Películas Populares - Seminario Android 2026

Una aplicación Android moderna, fluida y robusta diseñada para explorar películas populares utilizando la API de [The Movie Database (TMDB)](https://www.themoviedb.org/). El proyecto ha sido construido desde cero siguiendo las directrices oficiales de desarrollo de Google, priorizando la eficiencia en el uso de memoria, la reactividad de la interfaz y la tolerancia a fallos de red.

## 🚀 Características Principales

*   **Paginación Eficiente (Paging 3):** Implementación de scroll infinito mediante `LazyPagingItems` y `LazyVerticalGrid`. La aplicación solo renderiza los elementos visibles en pantalla y descarga datos en segundo plano bajo demanda, garantizando un consumo mínimo de memoria RAM y evitando errores de *OutOfMemory*.
*   **Búsqueda en Tiempo Real:** Barra de búsqueda integrada que filtra el catálogo de películas de forma reactiva. Utiliza corrutinas estructuradas para cancelar peticiones de red previas si el usuario sigue escribiendo, optimizando el ancho de banda.
*   **Soporte Nativo de Modo Oscuro (Material 3):** Diseño adaptativo basado en componentes semánticos de Material Design 3 (`Surface`, `Card`, `MaterialTheme.colorScheme`). La interfaz conmuta fluidamente entre los esquemas de color claro y oscuro del sistema operativo.
*   **Robustez y Tolerancia a Fallos:** Arquitectura diseñada para evitar cierres inesperados (*crashes*) o congelamientos de la UI (ANR). Todas las operaciones de I/O (red y disco) se ejecutan fuera del hilo principal. Ante pérdidas de conectividad, la app ofrece estados visuales de error controlados con mecanismos de **Reintento** unificados.

## 🛠️ Arquitectura del Software

El proyecto implementa **Clean Architecture** estructurada en tres capas independientes para facilitar la escalabilidad, el mantenimiento y las pruebas unitarias:

1.  **Data Layer:**
    *   Consumo de servicios REST con **Retrofit** y **OkHttp**.
    *   Interceptores para la autenticación segura mediante tokens.
    *   Estrategia de paginación con `PagingSource`.
    *   Mappers para transformar Data Transfer Objects (DTOs) en modelos de dominio puros.
2.  **Domain Layer:**
    *   Modelos de negocio independientes de librerías de terceros (`Movie`, `MovieDetail`).
    *   Casos de Uso (`UseCases`) que encapsulan las reglas de negocio individuales de la aplicación.
3.  **UI / Presentation Layer:**
    *   Interfaz declarativa construida íntegramente con **Jetpack Compose**.
    *   Patrón **MVVM (Model-View-ViewModel)**.
    *   Gestión de estados reactivos e inmutables mediante `StateFlow` y `MutableStateFlow`.

## 📦 Tecnologías y Librerías Utilizadas

*   **Jetpack Compose (1.7.5):** Toolkit moderno para el diseño de interfaces nativas.
*   **Material 3 (1.3.1):** Componentes visuales con soporte de diseño dinámico.
*   **Hilt (1.2.0 / Dagger):** Inyección de dependencias estándar de Android para desacoplar componentes.
*   **Paging 3:** Librería de Jetpack para la carga y presentación de datos paginados de forma eficiente.
*   **Navigation Compose:** Gestión de la navegación y paso de argumentos entre pantallas de forma declarativa.
*   **Retrofit 2 & OkHttp:** Cliente HTTP para la comunicación con la API de TMDB.
*   **Coil:** Carga e instanciación asíncrona de imágenes optimizada para Compose.

## 🔧 Configuración del Proyecto

Para compilar y ejecutar la aplicación localmente, es necesario configurar las credenciales de la API de TMDB de forma segura:

1. Solicita una API Key (v4 auth token) en el panel de desarrollador de [TMDB](https://www.themoviedb.org/settings/api).
2. En la raíz de tu proyecto local, abre o crea el archivo `local.properties`.
3. Agrega tu token de autenticación (asegúrate de no subir este archivo a repositorios públicos):

properties TMDB_API_KEY="TU_BEARER_ TOKEN_ AQUI" 

4. Sincroniza el proyecto con Gradle y ejecuta la aplicación en un emulador o dispositivo físico con Android 8.0 (API 26) o superior.