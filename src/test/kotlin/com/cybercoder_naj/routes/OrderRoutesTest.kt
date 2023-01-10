package com.cybercoder_naj.routes

import com.cybercoder_naj.models.orderStorage
import com.cybercoder_naj.testEnvironment
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlin.test.Test
import kotlin.test.assertEquals

class OrderRoutesTest {

    @Test
    fun getAllOrders() = testEnvironment {
        it.get("/order").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals(orderStorage, body())
        }
    }

    @Test
    fun getSpecificOrder() = testEnvironment {
        it.get("/order/2020-04-06-01").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals(orderStorage[0], body())
        }
    }

    @Test
    fun `Unknown order returns not found`() = testEnvironment {
        it.get("/order/2020-04-1").apply {
            assertEquals(HttpStatusCode.NotFound, status)
        }
    }

    @Test
    fun getTotalOrder() = testEnvironment {
        it.get("/order/2020-04-06-01/total").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals(23.15, body())
        }
    }
}