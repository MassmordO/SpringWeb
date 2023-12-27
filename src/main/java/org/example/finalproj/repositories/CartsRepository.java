package org.example.finalproj.repositories;

import org.example.finalproj.models.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartsRepository extends MongoRepository<Cart,String> {
    Cart findCartByUserId(String id);
}
