package org.example.finalproj.repositories;

import org.example.finalproj.models.UserStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatusesRepository extends MongoRepository<UserStatus,String> {
    UserStatus findUserStatusByDiscount(double discount);
}
