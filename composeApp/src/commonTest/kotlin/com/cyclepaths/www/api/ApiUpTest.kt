package com.cyclepaths.www.api

import com.cyclepaths.www.BACKEND_URL
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class ApiUpTest {

    private val client = HttpClient()

    @Test
    fun getHelloWorld() {
        runBlocking {
            val res = client.get(BACKEND_URL)

            assertEquals(HttpStatusCode.Companion.OK, res.status, "Should get OK Status")
            assertEquals(
                "Hello World!",
                res.bodyAsText(),
                "Should get \"Hello World!\". Got: " + res.bodyAsText()
            )
        }
    }
}