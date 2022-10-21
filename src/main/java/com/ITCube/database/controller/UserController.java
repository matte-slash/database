package com.ITCube.database.controller;

import com.ITCube.database.exception.ResourceNotFoundException;
import com.ITCube.database.model.User;
import com.ITCube.database.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
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
    public List<User> byName(@PathVariable String nome) throws ResourceNotFoundException {
        logger.info("GET_NOME");
        List<User> result= service.byNome(nome);
        if(result.size()!=0){
            return result;
        }else{
            throw new ResourceNotFoundException("No user with name "+ nome);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User create(@Valid @RequestBody User u){

        logger.info("POST");

        return service.create(u);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete( @PathVariable @Min(value = 1 , message= "Id must be equal or more than 1") long id)
            throws ResourceNotFoundException {

        logger.info("DELETE");
        try{
            service.delete(id);
        }catch(EmptyResultDataAccessException ex){
            throw new ResourceNotFoundException("User with id "+id +" is not present");
        }

    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@Min(value = 1 , message= "Id must be equal or more than 1")
                                                  @PathVariable long id,
                                              @Valid @RequestBody User u) throws ResourceNotFoundException {
        logger.info("PUT");
        User result= service.update(id, u);

        if(result!=null){
            return new ResponseEntity<>(result,HttpStatus.OK);
        }else{
            throw new ResourceNotFoundException("User with id "+id +" is not present");
        }

    }

}
