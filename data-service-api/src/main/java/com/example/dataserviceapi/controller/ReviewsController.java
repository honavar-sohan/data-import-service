package com.example.dataserviceapi.controller;

import com.example.dataserviceapi.entity.Reviews;
import com.example.dataserviceapi.model.request.ProductReviewModel;
import com.example.dataserviceapi.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewsController {

    @Autowired
    ReviewsService reviewsService;

    /**
    fetch reviuews and serve to users
     */
    @PostMapping(path = "/products")
    public List<Reviews> getProductReviews(@RequestBody ProductReviewModel productReviewModel) {
       List<Reviews> reviewsList = reviewsService.getProductReviews(productReviewModel.getProductId());

       return  reviewsList;
    }


}
