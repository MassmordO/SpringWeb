package org.example.finalproj.repositories;

import org.example.finalproj.models.UserAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountsRepository extends MongoRepository<UserAccount,String> {
    UserAccount findByUserMId(String id);
}
