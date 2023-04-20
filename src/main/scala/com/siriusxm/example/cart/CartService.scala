package com.siriusxm.example.cart

import cats.effect.{IO, Ref}

object CartService {
  private def updateCart(product: Product, amount: Int, cart: Cart): Cart =
    cart.cartItems.get(product.title) match {
      case Some(foundItem) =>
        // Found item in cart, so update its amount
        Cart(
          cart.cartItems + (product.title -> CartItem(
            foundItem.product,
            foundItem.amount + amount
          ))
        )
      case None =>
        // Did not find item in cart, so add a new CartItem
        Cart(cart.cartItems + (product.title -> CartItem(product, amount)))
    }

  def add(product: Product, amount: Int, cartDb: Ref[IO, Cart]): IO[Cart] =
    cartDb.updateAndGet(cart => updateCart(product, amount, cart))

  def viewCart(cartDb: Ref[IO, Cart]): IO[Cart] = cartDb.get

  def subtotal(cartDb: Ref[IO, Cart]): IO[Double] =
    for {
      cart <- cartDb.get
    } yield cart.cartItems.values
      .flatMap(p => List(p.product.price * p.amount))
      .sum

  def tax(cartDb: Ref[IO, Cart]): IO[Double] =
    subtotal(cartDb).map(total =>
      BigDecimal(total * .125)
        .setScale(2, BigDecimal.RoundingMode.HALF_UP)
        .doubleValue
    )

  def total(cartDb: Ref[IO, Cart]): IO[Double] =
    for {
      sTotal <- subtotal(cartDb)
      tx <- tax(cartDb)
    } yield sTotal + tx
}
