package com.example.jomajomadelivery.cart.controller;

import com.example.jomajomadelivery.cart.dto.request.AddCartRequestDto;
import com.example.jomajomadelivery.cart.dto.response.CartResponseDto;
import com.example.jomajomadelivery.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carts") //todo url은 팀원들과 상의 후 정리해야될듯합니다.
public class CartController {
    private final CartService cartService;

    @PostMapping
    public String add(@RequestBody AddCartRequestDto requestDto, Model model) {
        CartResponseDto responseDto = cartService.add();
        model.addAttribute("dto", requestDto);
        model.addAttribute("cart", responseDto);
//        System.out.println("cart " + requestDto.menuId());
//        System.out.println("cart " + requestDto.quantity());
        return "forward:/items";
    }

    @GetMapping("/{cartId}")
    public String find(@PathVariable Long cartId, Model model){
        CartResponseDto responseDto = cartService.find(cartId);
        model.addAttribute("cart", responseDto);
        // TODO:
        return "/carts";
    }

}
