package org.example.dataservice.writer;

import org.example.dataservice.entity.write.Reviews;
import org.example.dataservice.service.ReviewService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MySqlWriter implements ItemWriter<Reviews> {

    @Autowired
    private ReviewService reviewService;


    /**
    Database writer this function is used to write the processed data to db
     */
    @Override
    public void write(List<? extends Reviews> list) throws Exception {
        reviewService.saveReviews(list);
    }
}
