package org.example.finalproj.repositories;

import org.example.finalproj.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends MongoRepository<Role,String> {
    Role findRoleByName(String name);
}
