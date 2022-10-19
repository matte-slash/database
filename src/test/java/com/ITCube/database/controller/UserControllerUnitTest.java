package com.ITCube.database.controller;

import com.ITCube.database.model.User;
import com.ITCube.database.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerUnitTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public UserService serv;

    @Test
    void testList() throws Exception {

        User expected=new User("Matteo","Rosso","matteo@gmail.com","Grosseto");

        when(serv.list()).thenReturn(List.of(expected));

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].nome").value("Matteo"))
                .andExpect(jsonPath("$[0].cognome").value("Rosso"));
    }

    @Test
    void testByName() throws Exception{

        User expected=new User("Matteo","Rosso","matteo@gmail.com","Grosseto");

        when(serv.byNome(expected.getNome())).thenReturn(List.of(expected));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/Matteo"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].nome").value("Matteo"))
                .andExpect(jsonPath("$[0].cognome").value("Rosso"));

    }

    @Test
    void testCreate() throws Exception{

        User expected=new User("Matteo","Rosso","matteo@gmail.com","Grosseto");

        when(serv.create(any(User.class))).thenReturn(new ResponseEntity(expected, HttpStatus.OK));

        Gson gson = new Gson();


        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(expected)))
                .andDo(print())
                .andExpect(status().isOk())                                         //200 e non 201??
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("Matteo"))
                .andExpect(jsonPath("$.cognome").value("Rosso"));

    }

    @Test
    void testDelete() throws Exception {

        when(serv.delete(anyLong())).thenReturn(new ResponseEntity( HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/55"))    //Url ok?
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void testUpdate() throws Exception {

        User expected=new User("Matteo","Rosso","matteo@gmail.com","Grosseto");

        when(serv.update(anyLong(), any(User.class))).thenReturn(new ResponseEntity(expected, HttpStatus.OK));

        Gson gson = new Gson();


        mockMvc.perform(MockMvcRequestBuilders.put("/users/77" )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(expected)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("Matteo"))
                .andExpect(jsonPath("$.cognome").value("Rosso"));

    }

}
