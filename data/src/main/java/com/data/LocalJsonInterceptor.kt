package com.data

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class LocalJsonInterceptor(private val context: Context) : Interceptor {

    private val resourceMap = mapOf(
        "offers_tickets" to R.raw.offers_tickets,
        "tickets" to R.raw.tickets,
        "offers" to R.raw.offers
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toString()

        val fileName = when {
            uri.contains("offers_tickets") -> "offers_tickets.json"
            uri.contains("tickets") -> "tickets.json"
            uri.contains("offers") -> "offers.json"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }

        val resourceId = resourceMap[fileName.removeSuffix(".json")]
            ?: throw IllegalArgumentException("Unknown file name: $fileName")

        val json = readJsonFromRaw(resourceId)

        return Response.Builder()
            .code(200)
            .message("OK")
            .request(chain.request())
            .protocol(okhttp3.Protocol.HTTP_2)
            .body(json.toResponseBody("application/json".toMediaType()))
            .build()
    }

    private fun readJsonFromRaw(resourceId: Int): String {
        val inputStream = context.resources.openRawResource(resourceId)
        return inputStream.bufferedReader().use { it.readText() }
    }
}