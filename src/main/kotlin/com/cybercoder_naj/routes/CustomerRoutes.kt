package com.cybercoder_naj.routes

import com.cybercoder_naj.models.Customer
import com.cybercoder_naj.models.customerStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting() {
    route("/customer") {
        get {
            if (customerStorage.isNotEmpty())
                call.respond(
                    message = customerStorage
                )
            else
                call.respondText(
                    text = "No customers found",
                    status = HttpStatusCode.OK
                )
        }

        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                text = "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val customer =
                customerStorage.find { it.id == id } ?: return@get call.respondText(
                    text = "No customer with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(
                message = customer
            )
        }

        post {
            val customer = call.receive<Customer>()
            customerStorage.add(customer)
            call.respond(
                status = HttpStatusCode.Created,
                message = customerStorage
            )
        }

        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respondText(
                text = "Missing id",
                status = HttpStatusCode.BadRequest
            )
            if (customerStorage.removeIf { it.id == id })
                call.respond(
                    status = HttpStatusCode.Accepted,
                    message = customerStorage
                )
            else
                call.respondText(
                    text = "Not found",
                    status = HttpStatusCode.NotFound
                )
        }
    }
}