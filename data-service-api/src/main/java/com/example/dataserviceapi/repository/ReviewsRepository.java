package com.example.dataserviceapi.repository;

import com.example.dataserviceapi.entity.Reviews;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepository extends CrudRepository<Reviews, Long> {

    List<Reviews> findByProductId(long productId);
}
