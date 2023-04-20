package com.siriusxm.example

package object cart {
  case class Product(name: String, price: Double)
  case class CartItem(product: Product, amount: Int)
  case class Cart(cartItems: List[CartItem])
}
