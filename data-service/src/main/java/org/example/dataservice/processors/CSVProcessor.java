package org.example.dataservice.processors;

import org.example.dataservice.entity.read.ReadReviews;
import org.example.dataservice.entity.write.Reviews;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

@Component
public class CSVProcessor implements ItemProcessor<ReadReviews, Reviews> {
    @Override
    public Reviews process(ReadReviews readReviews) throws Exception {
        String createdAt = readReviews.getCreatedAt();
        String updatedAt = readReviews.getUpdatedAt();
        Reviews reviews = new Reviews();
        BeanUtils.copyProperties(reviews, readReviews);
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date createdDate = format.parse(createdAt);
        Date updatedDate = format.parse(updatedAt);
        reviews.setCreatedAt(createdDate);
        reviews.setUpdatedAt(updatedDate);
        return  reviews;

    }
}
