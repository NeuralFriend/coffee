package my.app.coffee.ui.screens.cart

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import my.app.coffee.core.CartManager

class CartViewModel : ViewModel() {
    val cartItems: SnapshotStateList<CartManager.CartItem> = CartManager.cartItems

    fun increase(itemId: Int) {
        CartManager.increase(itemId)
    }

    fun decrease(itemId: Int) {
        CartManager.decrease(itemId)
    }

    fun clear() {
        CartManager.clear()
    }

    fun totalPrice(): Double {
        return cartItems.sumOf { it.item.price * it.count.value }
    }
}