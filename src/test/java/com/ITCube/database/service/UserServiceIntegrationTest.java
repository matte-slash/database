package com.ITCube.database.service;

import com.ITCube.database.exception.ResourceNotFoundException;
import org.junit.jupiter.api.*;
import com.ITCube.database.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceIntegrationTest {

    @Autowired
    UserService service;

    Logger logger= LoggerFactory.getLogger(UserServiceIntegrationTest.class);



    @Test
    void testCreate(){
        //Arrange
        User expected=new User("Matteo", "Rosso", "matteo@gmail.com", "Grosseto");

        //Action
        User found=service.create(expected);


        //Assert
        assertThat(found).isNotNull();
        assertThat(found.getNome()).isEqualTo(expected.getNome());
        assertThat(found.getCognome()).isEqualTo(expected.getCognome());
        assertThat(found.getCitta()).isEqualTo(expected.getCitta());

        logger.info("Test_Create");

    }

    @Test
    void testList(){

        //Arrange
        User expected=new User("Matteo", "Rosso", "matteo@gmail.com", "Grosseto");
        service.create(expected);

        //Action
        List<User> l=service.list();

        //Assert
        assertThat(l).isNotNull();
        assertThat(l.size()).isEqualTo(1);
        assertThat(l.get(0).getNome()).isEqualTo(expected.getNome());

        logger.info("Test_List");

    }

    @Test
    void testByNome(){

        //Arrange
        User u1=new User("Pippo", "Rosso", "matteo@gmail.com", "Grosseto");
        User u2=new User("Pippo", "Rosso", "matteo@gmail.com", "Grosseto");
        service.create(u1);
        service.create(u2);

        //Action
        List<User> expected=service.byNome("pippo");

        //Assert
        assertThat(expected).isNotNull();
        assertThat(expected.size()).isEqualTo(2);
        assertThat(expected.get(0).getNome()).isEqualTo(expected.get(1).getNome());

        logger.info("Test_Nome");
    }


    @Test
    void testDelete(){

        //Arrange
        User u1=new User("Luca", "Rosso", "Luca@gmail.com", "Grosseto");
        service.create(u1);


        //Action
        service.delete(1);
        List<User> l=service.list();

        //Assert
        assertThat(l).isNotNull();
        assertThat(l.size()).isEqualTo(0);

        logger.info("Test_Delete");
    }

    @Test
    void testUpdate() throws ResourceNotFoundException {

        //Arrange
        User u1=new User("Claudio", "Rosso", "@gmail.com", "Grosseto");
        User u2=new User("Claudio", "Rosso", "NuovEmail@gmail.com", "Grosseto");
        u1=service.create(u1);

        //Action
        User expected = service.update(u1.getId(),u2);

        //Assert
        assertThat(expected).isNotNull();
        assertThat(expected.getEmail()).isEqualTo(u2.getEmail());

        logger.info("Test_Update");
    }

}
