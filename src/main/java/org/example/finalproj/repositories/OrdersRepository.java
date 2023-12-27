package org.example.finalproj.repositories;

import org.example.finalproj.models.Order;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends MongoRepository<Order,String> {
    List<Order> findAllByUserAccountId(String id);
}
