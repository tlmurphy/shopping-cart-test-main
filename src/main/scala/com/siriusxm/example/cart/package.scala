package com.siriusxm.example

package object cart {
  trait ProductName
  case object Cheerios extends ProductName
  case object CornFlakes extends ProductName
  case object Frosties extends ProductName
  case object Shreddies extends ProductName
  case object Weetabix extends ProductName
  case class Product(title: String, price: Double)
  case class CartItem(product: Product, amount: Int)
  case class Cart(cartItems: Map[String, CartItem])
}
