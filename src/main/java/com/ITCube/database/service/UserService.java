package com.ITCube.database.service;

import com.ITCube.database.model.User;
import com.ITCube.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public User create(User u) {

       User result= rep.save(new User(u.getNome(), u.getCognome(), u.getEmail(), u.getCitta()));

       return result;

    }

    public void delete(long id) {

       rep.deleteById(id);

    }

    public User update(long id, User u) {

        Optional<User> userData=rep.findById(id);
        if(userData.isPresent()) {
            User user=userData.get();
            user.setNome(u.getNome());
            user.setCognome(u.getCognome());
            user.setEmail(u.getEmail());
            user.setCitta(u.getCitta());
            return rep.save(user);
        }else{
            return null;
        }

    }

}
