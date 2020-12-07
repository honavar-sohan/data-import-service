package org.example.dataservice.service;

import org.example.dataservice.entity.write.Reviews;
import org.example.dataservice.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    public Reviews saveReview(Reviews r) {
        return reviewRepository.save(r);
    }

    public void saveReviews(List<? extends Reviews> list) {
         reviewRepository.saveAll(list);
    }
}
