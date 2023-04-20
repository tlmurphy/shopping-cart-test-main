package com.siriusxm.example.cart

import io.circe.syntax.*
import cats.effect.IO
import org.http4s.{HttpRoutes, HttpApp}
import org.http4s.client.Client
import org.http4s.circe.*
import org.http4s.dsl.io.*
import io.circe.generic.auto.*

object ProductApiMock {
  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> _ / "cheerios.json" =>
      Ok(Product("Cheerios", 8.43).asJson)
    case GET -> _ / "cornflakes.json" =>
      Ok(Product("Corn Flakes", 2.52).asJson)
    case GET -> _ / "frosties.json" =>
      Ok(Product("Frosties", 4.99).asJson)
    case GET -> _ / "shreddies.json" =>
      Ok(Product("Shreddies", 4.68).asJson)
    case GET -> _ / "weetabix.json" =>
      Ok(Product("Weetabix", 9.98).asJson)
  }

  val httpApp: HttpApp[IO] = routes.orNotFound
  val mockClient: Client[IO] = Client.fromHttpApp(httpApp)
}
