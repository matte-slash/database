package com.ITCube.database.model;


import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;


import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTest {

    private final Validator validator= (Validator) Validation.buildDefaultValidatorFactory().getValidator();
    @Test
    void createUser(){
        User user=new User("Matteo", "Rosso", "matteo@gmail.com", "Grosseto");
        assertNotNull(user);
        assertEquals("Grosseto", user.getCitta());
    }

    @Test
    void nameMustBeNotBlank(){
        User user=new User("","Rosso", "matteo@gmail.com", "Grosseto");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void emailMustBeNotNull(){
        User user=new User("Matteo", "Rosso", null, "Grosseto");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).isNotEmpty();
    }

}
