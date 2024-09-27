package com.epam.capstone.service;

import com.epam.capstone.model.Cart;
import com.epam.capstone.model.CartItem;
import com.epam.capstone.model.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    public void removeItems(Long itemId, Cart cart) {
        cart.getItems().removeIf(item -> item.getItem().getId().equals(itemId));
    }

    public Cart addItems(Item item, int quantity, Cart cart) {
        List<CartItem> cartItems = cart.getItems();
        boolean itemExists = false;

        for (CartItem cartItem : cartItems) {
            if (cartItem.getItem().getId().equals(item.getId())) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            cartItems.add(new CartItem(item, quantity));
        }

        cart.setItems(cartItems);
        return cart;
    }
}
