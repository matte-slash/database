package com.ITCube.database.service;

import com.ITCube.database.exception.ResourceNotFoundException;
import com.ITCube.database.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserServiceInterface {

    List<User> list();

    List<User> byNome(String nome);

    User create(User u);

    void delete(long id);

    User update(long id, User u) throws ResourceNotFoundException;


}
