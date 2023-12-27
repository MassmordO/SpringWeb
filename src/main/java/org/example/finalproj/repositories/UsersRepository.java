package org.example.finalproj.repositories;

import org.example.finalproj.models.UserM;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends MongoRepository<UserM, String> {
    UserM findUserMByEmail(String email);
}
