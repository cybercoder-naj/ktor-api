package com.cybercoder_naj

import com.cybercoder_naj.plugins.configureRouting
import com.cybercoder_naj.plugins.configureSerialization
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*

fun testEnvironment(block: suspend ApplicationTestBuilder.(HttpClient) -> Unit) = testApplication {
    application {
        configureSerialization()
        configureRouting()
    }

    val client = createClient {
        install(ContentNegotiation) {
            json()
        }
    }

    block(client)
}