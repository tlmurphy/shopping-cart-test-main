package com.siriusxm.example.cart

import cats.effect.{IO, Resource}
import munit.catseffect.IOFixture
import munit.CatsEffectSuite

class CartServiceSuite extends CatsEffectSuite {
  private val cart = Cart(
    Map(
      "Cornflakes" -> CartItem(Product("Cornflakes", 2.52), 2),
      "Weetabix" -> CartItem(Product("Weetabix", 9.98), 1)
    )
  )

  private val cartFixture = ResourceTestLocalFixture(
    "cart-fixture",
    Resource.make(IO.ref(cart))(_ => IO.unit)
  )

  override def munitFixtures: List[IOFixture[_]] = List(cartFixture)

  test("add") {
    val frosties = Product("Frosties", 4.99)
    CartService
      .add(frosties, 5, cartFixture())
      .assertEquals(
        Cart(
          Map(
            "Cornflakes" -> CartItem(Product("Cornflakes", 2.52), 2),
            "Weetabix" -> CartItem(Product("Weetabix", 9.98), 1),
            "Frosties" -> CartItem(frosties, 5)
          )
        )
      )
  }

  test("viewCart") {
    CartService.viewCart(cartFixture()).assertEquals(cart)
  }

  test("subtotal") {
    CartService.subtotal(cartFixture()).assertEquals(15.02)
  }

  test("tax") {
    CartService.tax(cartFixture()).assertEquals(1.88)
  }

  test("total") {
    CartService.total(cartFixture()).assertEquals(16.90)
  }
}
