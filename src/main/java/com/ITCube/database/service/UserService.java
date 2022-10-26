package com.ITCube.database.service;

import com.ITCube.database.exception.ResourceNotFoundException;
import com.ITCube.database.model.User;
import com.ITCube.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserServiceInterface{

    private final UserRepository rep;

    @Autowired
    public UserService(UserRepository rep){
        this.rep=rep;
    }


    public List<User> list() {

        Iterable<User> i=rep.findAll();

        List<User> result=new ArrayList<>();

        for(User u:i){
            result.add(u);
        }
        return result;
    }

    public List<User> byNome(String nome) {

        Iterable<User> i=rep.findByNomeIgnoreCase(nome);
        List<User> result=new ArrayList<>();

        for(User u:i){
            result.add(u);
        }
        return result;

    }

    public User create(User u) {

       return rep.save(new User(u.getNome(), u.getCognome(), u.getEmail(), u.getCitta()));

    }

    public void delete(long id) {

       rep.deleteById(id);

    }

    public User update(long id, User u) throws ResourceNotFoundException {


        User result=rep.findById(id).orElseThrow(()->new ResourceNotFoundException("User "+id+" not found"));

        result.setNome(u.getNome());
        result.setCognome(u.getCognome());
        result.setEmail(u.getEmail());
        result.setCitta(u.getCitta());
        return rep.save(result);

    }

}
