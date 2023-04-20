package com.siriusxm.example.cart

import cats.effect.IO
import munit.CatsEffectSuite
import munit.catseffect.IOFixture
import org.http4s.ember.client.EmberClientBuilder

class ProductServiceSuite extends CatsEffectSuite {
  private val clientFixture = ResourceSuiteLocalFixture(
    "client-fixture",
    EmberClientBuilder
      .default[IO]
      .build
  )

  override def munitFixtures: List[IOFixture[_]] = List(clientFixture)

  test("getProduct(Cheerios)") {
    val productService = new ProductService(clientFixture())
    productService
      .getProduct(Cheerios)
      .assertEquals(Product("Cheerios", 8.43))
  }

  test("getProduct(Cornflakes)") {
    val productService = new ProductService(clientFixture())
    productService
      .getProduct(CornFlakes)
      .assertEquals(Product("Corn Flakes", 2.52))
  }

  test("getProduct(Frosties)") {
    val productService = new ProductService(clientFixture())
    productService
      .getProduct(Frosties)
      .assertEquals(Product("Frosties", 4.99))
  }

  test("getProduct(Shreddies)") {
    val productService = new ProductService(clientFixture())
    productService
      .getProduct(Shreddies)
      .assertEquals(Product("Shreddies", 4.68))
  }

  test("getProduct(Weetabix)") {
    val productService = new ProductService(clientFixture())
    productService
      .getProduct(Weetabix)
      .assertEquals(Product("Weetabix", 9.98))
  }
}
