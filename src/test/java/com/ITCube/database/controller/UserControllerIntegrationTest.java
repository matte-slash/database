package com.ITCube.database.controller;

import com.ITCube.database.model.User;
import com.google.gson.Gson;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    public MockMvc mockMvc;

    @Order(2)
    @Test
    void testList() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].nome").value("Matteo"))
                .andExpect(jsonPath("$[0].cognome").value("Rosso"));

    }

    @Order(1)
    @Test
    void testCreateUser() throws Exception {

        User expected=new User("Matteo","Rosso","matteo@gmail.com","Grosseto");

        Gson gson=new Gson();

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(expected)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("Matteo"))
                .andExpect(jsonPath("$.cognome").value("Rosso"));
    }

    @Order(3)
    @Test
    void testByName() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/users/matteo"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].nome").value("Matteo"))
                .andExpect(jsonPath("$[0].cognome").value("Rosso"));

    }

    @Order(4)
    @Test
    void testUpdate() throws Exception {

        User expected=new User("Matteo","Rosso","NUOVA_MAIL@gmail.com","Grosseto");

        Gson gson=new Gson();

        mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(expected)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("Matteo"))
                .andExpect(jsonPath("$.email").value("NUOVA_MAIL@gmail.com"));

    }

    @Order(5)
    @Test
    void testDelete() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

    }

}
