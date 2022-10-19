package com.ITCube.database.repository;

import com.ITCube.database.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {


    Iterable<User> findByNomeIgnoreCase(String nome);
}
