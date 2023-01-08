package com.cybercoder_naj.routes

import com.cybercoder_naj.models.Order
import com.cybercoder_naj.models.orderStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.listOrdersRoute() {
    get("/order") {
        if (orderStorage.isNotEmpty())
            call.respond(
                status = HttpStatusCode.OK,
                message = orderStorage
            )
        else
            call.respondText(
                text = "No orders are found",
                status = HttpStatusCode.OK
            )
    }
}

fun Route.getOrderRoute() {
    get("/order/{number?}") {
        val number = call.parameters["number"] ?: return@get call.respondText(
            text = "Missing number",
            status = HttpStatusCode.BadRequest
        )

        val order = orderStorage.find { it.number == number } ?: return@get call.respondText(
            text = "Order not found with number $number",
            status = HttpStatusCode.NotFound
        )

        call.respond(
            message = order,
            status = HttpStatusCode.OK
        )
    }
}

fun Route.totalizeOrderRoute() {
    get("/order/{number?}/total") {
        val number = call.parameters["number"] ?: return@get call.respondText(
            text = "Missing number",
            status = HttpStatusCode.BadRequest
        )

        val order = orderStorage.find { it.number == number } ?: return@get call.respondText(
            text = "Order not found with number $number",
            status = HttpStatusCode.NotFound
        )

        val total = order.contents.sumOf { it.price * it.amount }
        call.respond(
            message = total,
            status = HttpStatusCode.OK
        )
    }
}