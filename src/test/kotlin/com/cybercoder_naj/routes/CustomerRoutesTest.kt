package com.cybercoder_naj.routes

import com.cybercoder_naj.models.Customer
import com.cybercoder_naj.testEnvironment
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlin.test.Test
import kotlin.test.assertEquals

class CustomerRoutesTest {

    @Test
    fun `adding new customer`() = testEnvironment {
        it.post("/customer") {
            contentType(ContentType.Application.Json)
            setBody(Customer("100", "Jane", "Smith", "jane.smith@company.com"))
        }.apply {
            assertEquals(mutableListOf(Customer("100", "Jane", "Smith", "jane.smith@company.com")), body())
            assertEquals(HttpStatusCode.Created, status)
        }
    }

    @Test
    fun `add another customer`() = testEnvironment {
        it.post("/customer") {
            contentType(ContentType.Application.Json)
            setBody(Customer("300", "Mary", "Smith", "mary.smith@company.com"))
        }.apply {
            assertEquals(
                mutableListOf(
                    Customer("100", "Jane", "Smith", "jane.smith@company.com"),
                    Customer("300", "Mary", "Smith", "mary.smith@company.com")
                ), body()
            )
            assertEquals(HttpStatusCode.Created, status)
        }
    }

    @Test
    fun `get unknown customer returns not found`() = testEnvironment {
        it.get("/customer/200").apply {
            assertEquals("No customer with id 200", bodyAsText())
            assertEquals(HttpStatusCode.NotFound, status)
        }
    }

    @Test
    fun `get known customer`() = testEnvironment {
        it.get("/customer/300").apply {
            assertEquals(Customer("300", "Mary", "Smith", "mary.smith@company.com"), body())
            assertEquals(HttpStatusCode.OK, status)
        }
    }

    @Test
    fun `delete unknown customer returns not found`() = testEnvironment {
        it.delete("/customer/200").apply {
            assertEquals("Not found", bodyAsText())
            assertEquals(HttpStatusCode.NotFound, status)
        }
    }

    @Test
    fun `delete known customer`() = testEnvironment {
        it.delete("/customer/300").apply {
            assertEquals(mutableListOf(Customer("100", "Jane", "Smith", "jane.smith@company.com")), body())
            assertEquals(HttpStatusCode.Accepted, status)
        }
    }
}