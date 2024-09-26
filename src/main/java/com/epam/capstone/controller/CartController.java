package com.epam.capstone.controller;

import com.epam.capstone.model.Cart;
import com.epam.capstone.model.Item;
import com.epam.capstone.model.Person;
import com.epam.capstone.service.CartService;
import com.epam.capstone.service.ItemsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ItemsService itemsService;
    private final Cart cart;

    @Autowired
    public CartController(CartService cartService, ItemsService itemsService, Cart cart) {
        this.cartService = cartService;
        this.itemsService = itemsService;
        this.cart = cart;
    }

    @GetMapping
    public String viewCart(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
        model.addAttribute("isAuthenticated", isAuthenticated);


        model.addAttribute("cart", cart);
        model.addAttribute("total", cart.getItems().stream().mapToDouble(x -> x.getItem().getPrice() * x.getQuantity()).sum());


        return "store/cart";
    }

    @PostMapping("/add")
    public String addCart(@RequestParam("itemId") Long itemId,
                            @RequestParam("quantity") Integer quantity) {
        Item item = itemsService.getItemById(itemId);
        cartService.addItems(item, quantity, cart);

        return "redirect:/store";
    }


    @DeleteMapping("/{id}")
    public String deleteCart(@PathVariable Long id) {
        if(cart != null) {
            cartService.removeItems(id, cart);
        }

        return "redirect:/cart";
    }
}
