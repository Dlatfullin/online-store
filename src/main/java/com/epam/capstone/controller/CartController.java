package com.epam.capstone.controller;

import com.epam.capstone.model.Cart;
import com.epam.capstone.model.Item;
import com.epam.capstone.service.CartService;
import com.epam.capstone.service.ItemsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ItemsService itemsService;

    @Autowired
    public CartController(CartService cartService, ItemsService itemsService) {
        this.cartService = cartService;
        this.itemsService = itemsService;
    }

    @GetMapping
    public String viewBucket(HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        model.addAttribute("cart", cart);

        return "store/cart";
    }

    @PostMapping("/add")
    public String addCart(HttpSession session, @RequestParam("itemId") Long itemId,
                            @RequestParam("quantity") Integer quantity) {
        Item item = itemsService.getItemById(itemId);
        session.setAttribute("cart", cartService.addItems(item, quantity));

        return "redirect:/store";
    }
    

    @DeleteMapping("/{id}")
    public String deleteBucket(HttpSession session, @PathVariable Long id) {
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart != null) {
            cartService.removeItems(id);
        }

        return "redirect:/cart";
    }
}
