package com.ITCube.database.controller;

import com.ITCube.database.exception.ResourceNotFoundException;
import com.ITCube.database.model.User;
import com.ITCube.database.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerUnitTest {

    @Mock
    private UserService serv;

    @InjectMocks
    private UserController controller;

    @Test
    void testList() throws Exception {

        User expected=new User("Matteo","Rosso","matteo@gmail.com","Grosseto");

        when(serv.list()).thenReturn(List.of(expected));

        User result=controller.list().get(0);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expected);
        verify(serv,times(1)).list();
        verifyNoMoreInteractions(serv);

    }

    @Test
    void testByName() throws Exception{

        User expected=new User("Matteo","Rosso","matteo@gmail.com","Grosseto");

        when(serv.byNome(expected.getNome())).thenReturn(List.of(expected));

        User result=controller.byName(expected.getNome()).get(0);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expected);
        verify(serv,times(1)).byNome("Matteo");
        verifyNoMoreInteractions(serv);

    }

    @Test
    void testCreate() throws Exception{

        User expected=new User("Matteo","Rosso","matteo@gmail.com","Grosseto");

        when(serv.create(any(User.class))).thenReturn(expected);

        User result=controller.create(expected);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expected);
        verify(serv,times(1)).create(expected);
        verifyNoMoreInteractions(serv);

    }


    @Test
    void testUpdate() throws Exception {

        User expected=new User("Matteo","Rosso","matteo@gmail.com","Grosseto");

        when(serv.update(1, expected)).thenReturn(expected);

        User result=controller.update(1, expected);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expected);
        verify(serv,times(1)).update(1, expected);
        verifyNoMoreInteractions(serv);

    }

    @Test
    void updateExceptiontest() throws Exception {

        User expected=new User("Matteo","Rosso","matteo@gmail.com","Grosseto");
        when(serv.update(1, expected)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class , ()->controller.update(1,expected));

    }

}
