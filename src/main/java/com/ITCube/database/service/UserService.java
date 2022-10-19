package com.ITCube.database.service;

import com.ITCube.database.model.User;
import com.ITCube.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface{

    private final UserRepository rep;

    @Autowired
    public UserService(UserRepository rep){
        this.rep=rep;
    }


    public List<User> list() {

        Iterable<User> i=rep.findAll();

        List<User> result=new ArrayList<User>();

        for(User u:i){
            result.add(u);
        }
        return result;
    }

    public List<User> byNome(String nome) {

        Iterable<User> i=rep.findByNomeIgnoreCase(nome);
        List<User> result=new ArrayList<User>();

        for(User u:i){
            result.add(u);
        }
        return result;

    }

    public ResponseEntity<User> create(User u) {

        try{
            User user=rep.save(new User(u.getNome(), u.getCognome(), u.getEmail(), u.getCitta()));
            return new  ResponseEntity<>(user , HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<HttpStatus> delete(long id) {

        try{
            rep.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

    public ResponseEntity<User> update(long id, User u) {

        Optional<User> userData=rep.findById(id);
        if(userData.isPresent()) {
            User user=userData.get();
            user.setNome(u.getNome());
            user.setCognome(u.getCognome());
            user.setEmail(u.getEmail());
            user.setCitta(u.getCitta());
            return new ResponseEntity<>(rep.save(user), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
