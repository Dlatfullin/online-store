package com.epam.capstone.service;

import com.epam.capstone.model.Cart;
import com.epam.capstone.model.CartItem;
import com.epam.capstone.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final Cart cart;

    @Autowired
    public CartService(Cart cart) {
        this.cart = cart;
    }

    public void removeItems(Long itemId) {
        cart.getItems().removeIf(item -> item.getItem().getId().equals(itemId));
    }

    public Cart addItems(Item item, int quantity) {
        List<CartItem> cartItems = cart.getItems();
        if(cartItems.size() > 0) {
            for (CartItem cartItem : cartItems) {
                if (cartItem.getItem().getId().equals(item.getId())) {
                    cartItem.setQuantity(cartItem.getQuantity() + quantity);
                }
            }
        }

        cartItems.add(new CartItem(item, quantity));
        cart.setItems(cartItems);

        return cart;
    }
}
