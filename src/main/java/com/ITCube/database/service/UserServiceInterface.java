package com.ITCube.database.service;

import com.ITCube.database.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserServiceInterface {

    List<User> list();

    List<User> byNome(String nome);

    ResponseEntity<User> create(User u);

    ResponseEntity<HttpStatus> delete(long id);

    ResponseEntity<User> update(long id, User u);



}
