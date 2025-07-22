package my.app.coffee.core

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import my.app.coffee.model.MenuItem

object CartManager {
    data class CartItem(val item: MenuItem, val count: MutableState<Int> = mutableStateOf(1))

    val cartItems = mutableStateListOf<CartItem>()

    fun addItem(item: MenuItem) {
        val existing = cartItems.find { it.item.id == item.id }
        if (existing != null) {
            existing.count.value++
        } else {
            cartItems.add(CartItem(item))
        }
    }

    fun increase(itemId: Int) {
        val item = cartItems.find { it.item.id == itemId }
        if (item != null) {
            item.count.value += 1
        }
    }

    fun decrease(itemId: Int) {
        val item = cartItems.find { it.item.id == itemId }
        if (item != null) {
            if (item.count.value > 1) {
                item.count.value--
            } else {
                cartItems.remove(item)
            }
        }
    }

    fun clear() {
        cartItems.clear()
    }
}