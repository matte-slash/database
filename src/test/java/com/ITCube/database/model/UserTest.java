package com.ITCube.database.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTest {

    @Test
    void createUser(){
        User user=new User("Matteo", "Rosso", "matteo@gmail.com", "Grosseto");
        assertNotNull(user);
        assertEquals("Grosseto", user.getCitta());
    }

}
