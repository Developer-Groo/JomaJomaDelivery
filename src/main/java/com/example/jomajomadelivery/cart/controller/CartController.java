package com.example.jomajomadelivery.cart.controller;

import com.example.jomajomadelivery.cart.dto.request.AddCartRequestDto;
import com.example.jomajomadelivery.cart.dto.response.CartResponseDto;
import com.example.jomajomadelivery.cart.service.CartService;
import com.example.jomajomadelivery.user.entity.RoleConstants;
import com.example.jomajomadelivery.common.aop.account.CurrentUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carts")
@Secured(RoleConstants.ROLE_USER)
public class CartController {
    private final CartService cartService;

    @PostMapping
    public String addOrGetCart(@RequestBody AddCartRequestDto requestDto, Model model,
                      @CurrentUserId Long userId) {
        CartResponseDto responseDto = cartService.getUserCart(userId);
        model.addAttribute("dto", requestDto);
        model.addAttribute("cart", responseDto);
        return "forward:/carts/"+responseDto.cartId()+"/items";
    }

    @GetMapping("/{cartId}")
    public String find(@PathVariable Long cartId, Model model){
        CartResponseDto responseDto = cartService.find(cartId);
        model.addAttribute("cart", responseDto);
        return "/carts";
    }
    @GetMapping
    public String getCartByUser(@CurrentUserId Long userId, Model model){
        CartResponseDto responseDto = cartService.getUserCart(userId);
        model.addAttribute("cart", responseDto);
        return "CartList";
    }

}
