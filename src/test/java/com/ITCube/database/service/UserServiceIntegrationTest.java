package com.ITCube.database.service;

import org.junit.jupiter.api.*;
import com.ITCube.database.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceIntegrationTest {

    @Autowired
    UserService service;

    Logger logger= LoggerFactory.getLogger(UserServiceIntegrationTest.class);



    @Order(2)
    @Test
    void testCreate(){
        //Arrange
        User expected=new User("Matteo", "Rosso", "matteo@gmail.com", "Grosseto");

        //Action
        User found=service.create(expected).getBody();


        //Assert
        assertThat(found).isNotNull();
        assertThat(found.getNome()).isEqualTo(expected.getNome());
        assertThat(found.getCognome()).isEqualTo(expected.getCognome());
        assertThat(found.getCitta()).isEqualTo(expected.getCitta());

        logger.info("Test_Create");

    }

    @Order(1)
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

    @Order(3)
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

    @Order(4)
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
        assertThat(l.size()).isEqualTo(4);
        assertThat(l.get(3).getId()).isEqualTo(5);
        assertThat(l.get(3).getNome()).isEqualTo(u1.getNome());

        logger.info("Test_Delete");
    }

    @Test
    @Order(5)
    void testUpdate(){

        //Arrange
        User u1=new User("Claudio", "Rosso", "@gmail.com", "Grosseto");
        User u2=new User("Claudio", "Rosso", "NuovEmail@gmail.com", "Grosseto");
        u1=service.create(u1).getBody();

        //Action
        User expected = service.update(u1.getId(),u2).getBody();

        //Assert
        assertThat(expected).isNotNull();
        assertThat(expected.getEmail()).isEqualTo(u2.getEmail());

        logger.info("Test_Update");
    }

}
