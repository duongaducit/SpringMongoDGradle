package com.asiantech.ducdh.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.asiantech.ducdh.demo.entity.User;

@RepositoryRestResource
public interface UserRepository extends MongoRepository<User, String>{

		User findByEmail(@Param("email")String email);
}
