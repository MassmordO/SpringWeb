package org.example.finalproj.repositories;

import org.example.finalproj.models.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotosRepository extends MongoRepository<Photo,String> {
}
