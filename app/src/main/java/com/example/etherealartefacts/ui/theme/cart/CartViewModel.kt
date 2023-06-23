package com.example.etherealartefacts.ui.theme.cart

import androidx.lifecycle.ViewModel
import com.example.etherealartefacts.models.request.Order
import com.example.etherealartefacts.models.request.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {

    private val _orderedProducts = MutableStateFlow(emptyList<Product>())
    val orderedProducts = _orderedProducts.asStateFlow()

    private val _orderHistory = MutableStateFlow(emptyList<Order>())
    val orderHistory = _orderHistory.asStateFlow()

    private val _totalPrice = MutableStateFlow(0)
    val totalPrice = _totalPrice.asStateFlow()

    fun addToOrderedProducts(product: Product) {
        val currentProducts = _orderedProducts.value.toMutableList()
        val orderedProduct = currentProducts.firstOrNull { it.id == product.id }

        orderedProduct?.let {
            if (product.stock - orderedProduct.quantity > 0) {

                val updatedProduct = orderedProduct.copy(
                    stock = orderedProduct.stock - 1,
                    quantity = orderedProduct.quantity + 1,
                    isInStock = true
                )
                currentProducts[currentProducts.indexOf(orderedProduct)] = updatedProduct
                _orderedProducts.value = currentProducts
            }
        } ?: run {
            val newProduct = product.copy(stock = product.stock - 1, quantity = 1, isInStock = true)
            currentProducts.add(newProduct)
            _orderedProducts.value = currentProducts
        }

        updateTotalPrice()
    }

    fun clearOrderHistory() {
        clearCart()
        _orderHistory.value = emptyList()
    }

    fun clearCart() {
        _orderedProducts.value = emptyList()
        _totalPrice.value = 0
    }

    fun getQuantityForProduct(productId: Int): Int {
        val orderedProduct = _orderedProducts.value.firstOrNull { it.id == productId }
        orderedProduct?.let {
            return orderedProduct.quantity
        }
        return 0
    }

    fun removeProduct(product: Product) {
        val currentProducts = _orderedProducts.value.toMutableList()
        currentProducts.remove(product)
        _orderedProducts.value = currentProducts
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        var total = 0

        for (product in _orderedProducts.value) {
            total += product.price * product.quantity
        }

        _totalPrice.value = total
    }

    fun placeOrder() {
        val placedOrder = _orderedProducts.value.toList()
        val orderDate = Date()
        val totalPrice = _totalPrice.value

        val order = Order(placedOrder, orderDate, totalPrice)
        val updatedOrderHistory = _orderHistory.value.toMutableList()
        updatedOrderHistory.add(order)
        _orderHistory.value = updatedOrderHistory
        clearCart()
    }
}
