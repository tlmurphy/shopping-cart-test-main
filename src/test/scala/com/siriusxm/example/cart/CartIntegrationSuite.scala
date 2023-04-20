package com.siriusxm.example.cart

import cats.effect.{IO, Resource}
import munit.CatsEffectSuite
import munit.catseffect.IOFixture
import org.http4s.ember.client.EmberClientBuilder

class CartIntegrationSuite extends CatsEffectSuite {
  private val clientFixture = ResourceSuiteLocalFixture(
    "client-fixture",
    EmberClientBuilder
      .default[IO]
      .build
  )

  private val cartFixture = ResourceTestLocalFixture(
    "cart-fixture",
    Resource.make(IO.ref(Cart(Map())))(_ => IO.unit)
  )

  override def munitFixtures: List[IOFixture[_]] =
    List(clientFixture, cartFixture)

  test("get a product and add it to the cart") {
    val productService = new ProductService(clientFixture())
    for {
      product <- productService.getProduct(Cheerios)
      cart <- CartService.add(product, 5, cartFixture())
    } yield {
      assertEquals(
        cart,
        Cart(Map("Cheerios" -> CartItem(Product("Cheerios", 8.43), 5)))
      )
    }
  }

  test("get a product and add multiple items to the cart") {
    val productService = new ProductService(clientFixture())
    for {
      cheerios <- productService.getProduct(Cheerios)
      cornflakes <- productService.getProduct(CornFlakes)
      frosties <- productService.getProduct(Frosties)
      _ <- CartService.add(cheerios, 2, cartFixture())
      _ <- CartService.add(cornflakes, 3, cartFixture())
      cart <- CartService.add(frosties, 4, cartFixture())
    } yield {
      assertEquals(
        cart,
        Cart(
          Map(
            "Cheerios" -> CartItem(Product("Cheerios", 8.43), 2),
            "Corn Flakes" -> CartItem(Product("Corn Flakes", 2.52), 3),
            "Frosties" -> CartItem(Product("Frosties", 4.99), 4)
          )
        )
      )
    }
  }

  test("get a product and add it multiple times to the cart") {
    val productService = new ProductService(clientFixture())
    for {
      cheerios <- productService.getProduct(Cheerios)
      cornFlakes <- productService.getProduct(CornFlakes)
      _ <- CartService.add(cheerios, 2, cartFixture())
      _ <- CartService.add(cheerios, 7, cartFixture())
      _ <- CartService.add(cheerios, 100, cartFixture())
      _ <- CartService.add(cornFlakes, 100, cartFixture())
      _ <- CartService.add(cornFlakes, 2, cartFixture())
      _ <- CartService.add(cornFlakes, 800, cartFixture())
      cart <- CartService.add(cheerios, 500000, cartFixture())
    } yield {
      assertEquals(
        cart,
        Cart(
          Map(
            "Cheerios" -> CartItem(Product("Cheerios", 8.43), 500109),
            "Corn Flakes" -> CartItem(Product("Corn Flakes", 2.52), 902)
          )
        )
      )
    }
  }
}
