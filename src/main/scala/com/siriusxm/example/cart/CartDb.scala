package com.siriusxm.example.cart

object CartDb {
  def add(item: CartItem): Cart = ???
  def viewCart(cart: Cart): Unit = ???
  def subtotal(cart: Cart): Double = ???
  def tax(cart: Cart): Double = ???
  def total(cart: Cart): Double = ???
}
