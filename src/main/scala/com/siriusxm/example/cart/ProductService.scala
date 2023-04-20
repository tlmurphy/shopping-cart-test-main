package com.siriusxm.example.cart

import cats.effect.{IO, Resource}
import org.http4s.circe.jsonOf
import org.http4s.client.Client
import io.circe.generic.auto.*
import org.http4s.Uri
import org.http4s.implicits.uri

class ProductService(client: Client[IO]) {
  private def productUri(name: ProductName): Uri = {
    val uri =
      uri"https://raw.githubusercontent.com/mattjanks16/shopping-cart-test-data/main/"
    uri / s"${name.toString.toLowerCase}.json"
  }

  def getProduct(name: ProductName): IO[Product] =
    client.expect[Product](productUri(name))(jsonOf[IO, Product])
}
