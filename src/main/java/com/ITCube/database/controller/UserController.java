package com.ITCube.database.controller;

import com.ITCube.database.model.User;
import com.ITCube.database.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService service;

    Logger logger= LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService service) {
        this.service=service;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<User> list(){
        logger.info("GET");
        return service.list();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{nome}")
    public List<User> byName(@PathVariable String nome){
        logger.info("GET_NOME");
        return service.byNome(nome);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<User> create(@RequestBody User u){

        logger.info("POST");
        return service.create(u);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable long id){
        logger.info("DELETE");
        return service.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable long id, @RequestBody User u){
        logger.info("PUT");
        return service.update(id, u);

    }

}
