package ar.edu.unicen.seminarioandroid2026.ddl.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale

// Concepto clave para el alumno: El Interceptor actúa como un peaje transparente.
// Cualquier petición enviada por Retrofit pasará por este método, garantizando que
// todas las consultas incluyan el token y el idioma sin tener que escribirlo manualmente en cada endpoint.

class TmdbAuthInterceptor(
    private val apiKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        // 1. Detectar el idioma del dispositivo dinámicamente (ej: "es-AR", "en-US")
        val deviceLanguage = Locale.getDefault().language

        // 2. Agregar el query parameter 'language' a la URL
        val urlWithLanguage = originalUrl.newBuilder()
            .addQueryParameter("api_key", apiKey) // IMPORTANTE: TMDB v3 usa esto
            .addQueryParameter("language", deviceLanguage)
            .build()

        // 3. Crear la nueva petición adjuntando la URL con idioma y el Header de autorización Bearer
        val requestWithAuth = originalRequest.newBuilder()
            .url(urlWithLanguage)
            .addHeader("accept", "application/json")
            .build()

        return chain.proceed(requestWithAuth)
    }
}