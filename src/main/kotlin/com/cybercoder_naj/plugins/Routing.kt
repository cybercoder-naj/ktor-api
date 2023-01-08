package com.cybercoder_naj.plugins

import com.cybercoder_naj.routes.customerRouting
import com.cybercoder_naj.routes.getOrderRoute
import com.cybercoder_naj.routes.listOrdersRoute
import com.cybercoder_naj.routes.totalizeOrderRoute
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {
    routing {
        customerRouting()
        listOrdersRoute()
        getOrderRoute()
        totalizeOrderRoute()
    }
}
