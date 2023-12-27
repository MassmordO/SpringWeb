package org.example.finalproj.repositories;

import org.example.finalproj.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends MongoRepository<Product,String> {
    List<Product> findProductsByProductTypeId(String id);
    @Query("{ 'productType.$id' : { $ne : ?0 } }")
    List<Product> findByProductTypeIdNot(String id);
}
