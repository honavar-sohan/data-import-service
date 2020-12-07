package com.example.dataserviceapi.service;

import com.example.dataserviceapi.entity.Reviews;
import com.example.dataserviceapi.repository.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewsService {

    @Autowired
    ReviewsRepository reviewsRepository;


    public List<Reviews> getProductReviews(long productId) {
       List<Reviews> reviewsList =  reviewsRepository.findByProductId(productId);

        return  reviewsList;
    }
}
