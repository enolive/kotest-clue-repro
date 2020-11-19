package de.welcz.kotestcluerepro

import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
class ApplicationTests(private val environment: Environment,
                       private val webTestClient: WebTestClient) : FunSpec({
  test("application starts") {
    environment.shouldNotBeNull()
  }

  test("fail with clue using shouldBe assertion") {
    val response = webTestClient
      .get()
      .uri("/notexisting")
      .exchange()

    withClue("Fails with this clue") {
      response.expectStatus().value { it shouldBe HttpStatus.OK.value() }
    }
  }

  test("fail without clue using webTestClient assertion") {
    val response = webTestClient
      .get()
      .uri("/notexisting")
      .exchange()

    withClue("Fails without this clue") {
      response.expectStatus().isOk
    }
  }
})
