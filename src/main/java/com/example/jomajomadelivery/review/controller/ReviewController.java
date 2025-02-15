package com.example.jomajomadelivery.review.controller;

import com.example.jomajomadelivery.common.aop.account.CurrentUserId;
import com.example.jomajomadelivery.review.dto.request.ReviewCreateRequestDto;
import com.example.jomajomadelivery.review.dto.request.ReviewUpdateRequestDto;
import com.example.jomajomadelivery.review.dto.response.ReviewResponseDto;
import com.example.jomajomadelivery.review.service.ReviewService;
import com.example.jomajomadelivery.user.entity.RoleConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stores/{storeId}/reviews")
@RequiredArgsConstructor
@Secured(RoleConstants.ROLE_USER)
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/new")
    public String create(@PathVariable Long storeId, Model model) {
        model.addAttribute("storeId",storeId);
        return "CreateReview";
    }
//    @PostMapping
//    public ResponseEntity<ReviewResponseDto> create(@PathVariable Long storeId, ReviewCreateRequestDto dto) {
//        return new ResponseEntity<>(reviewService.create(storeId, dto), HttpStatus.CREATED);
//    }
    @PostMapping
    public String create(@PathVariable Long storeId, @CurrentUserId Long userId, ReviewCreateRequestDto dto) {
        reviewService.create(storeId, userId, dto);
        return "redirect:/stores/" + storeId + "/reviews";
    }

    @GetMapping
    public String findAllById(
            @PathVariable Long storeId,
            @RequestParam(defaultValue = "1") Integer minRating,
            @RequestParam(defaultValue = "5") Integer maxRating,
            Pageable pageable,
            Model model
    ) {
        model.addAttribute("storeId", storeId);
        model.addAttribute("reviews", reviewService.findAllById(storeId, minRating, maxRating, pageable));
        return "ReviewList";
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> update(@PathVariable Long id, @RequestBody ReviewUpdateRequestDto dto) {
        return new ResponseEntity<>(reviewService.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long reviewId, @CurrentUserId Long userId) {
        reviewService.delete(reviewId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
