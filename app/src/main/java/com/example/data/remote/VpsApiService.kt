package com.example.data.remote

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

@JsonClass(generateAdapter = true)
data class VpsHealthResponse(
    val status: String = "ok",
    val serverName: String = "Pterodactyl IX-H Node",
    val botSession: String = "WangLinMD",
    val connected: Boolean = true,
    val timestamp: Long = System.currentTimeMillis()
)

@JsonClass(generateAdapter = true)
data class PterodactylPowerRequest(
    val signal: String, // "start", "restart", "stop"
    val serverId: String = "ixh-wa-bot-01"
)

@JsonClass(generateAdapter = true)
data class VpsSyncPayload(
    val className: String = "IX-H • SMP Spensari",
    val totalStudents: Int,
    val confirmedCashBalance: Int,
    val attendanceDate: String = "2026-11-04",
    val presentCount: Int,
    val syncTimestamp: Long = System.currentTimeMillis()
)

@JsonClass(generateAdapter = true)
data class VpsActionResponse(
    val success: Boolean = true,
    val message: String = "OK"
)

interface VpsApiService {
    @GET("api/v1/health")
    suspend fun checkHealth(
        @Header("Authorization") authHeader: String
    ): Response<VpsHealthResponse>

    @POST("api/v1/pterodactyl/power")
    suspend fun sendPterodactylPower(
        @Header("Authorization") authHeader: String,
        @Body request: PterodactylPowerRequest
    ): Response<VpsActionResponse>

    @POST("api/v1/sync")
    suspend fun syncClassData(
        @Header("Authorization") authHeader: String,
        @Body payload: VpsSyncPayload
    ): Response<VpsActionResponse>
}

object VpsNetworkClient {
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun create(baseUrl: String): VpsApiService {
        val normalizedUrl = baseUrl.trim().let {
            if (!it.startsWith("http://") && !it.startsWith("https://")) {
                "https://$it"
            } else {
                it
            }
        }.let {
            if (it.endsWith("/")) it else "$it/"
        }

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val client = OkHttpClient.Builder()
            .connectTimeout(6, TimeUnit.SECONDS)
            .readTimeout(6, TimeUnit.SECONDS)
            .writeTimeout(6, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(normalizedUrl)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(VpsApiService::class.java)
    }
}
