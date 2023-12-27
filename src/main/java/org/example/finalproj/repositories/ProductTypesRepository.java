package org.example.finalproj.repositories;

import org.example.finalproj.models.ProductType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypesRepository extends MongoRepository<ProductType,String> {
    ProductType findProductTypeByName(String name);
}
