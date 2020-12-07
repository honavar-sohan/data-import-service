package org.example.dataservice.repository;

import org.example.dataservice.entity.write.Reviews;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Reviews, Long> {
}
