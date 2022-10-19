package com.ITCube.database.service;

import com.ITCube.database.model.User;
import com.ITCube.database.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository rep;
    @InjectMocks
    private UserService service;

    @Test
    @Order(1)
    void createTest(){
        User u=new User("Matteo","Rosso","matteo@gmail.com","Grosseto");
        when(rep.save(any(User.class))).thenReturn(u);

        final var actual=service.create(new User());

        assertThat(actual.getBody()).isEqualTo(u);
        verify(rep, times(1)).save(any(User.class));
        verifyNoMoreInteractions(rep);
    }

    @Test
    @Order(2)
    void byNometest(){

        final var expectedUser=new User("Matteo","Rosso","matteo@gmail.com","Grosseto");
        when(rep.findByNomeIgnoreCase(expectedUser.getNome())).thenReturn(List.of(expectedUser));

        final var actual=service.byNome(expectedUser.getNome()).get(0);

        assertThat(actual).isEqualTo(expectedUser);
        verify(rep, times(1)).findByNomeIgnoreCase(anyString());
        verifyNoMoreInteractions(rep);

    }

    @Test
    @Order(3)
    void listTest(){

        when(rep.findAll()).thenReturn(List.of(new User()));

        assertFalse(service.list().isEmpty());
        assertThat(service.list()).asList().hasSize(1);
        verify(rep, times(2)).findAll();
        verifyNoMoreInteractions(rep);

    }

    @Test
    @Order(4)
    void deleteTest(){

        doNothing().when(rep).deleteById(anyLong());

        service.delete(anyLong());
        verify(rep,times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(rep);
    }

    @Test
    @Order(5)
    void updateTest(){

        final var expectedUser=new User("Matteo","Rosso","matteo@gmail.com","Grosseto");
        final var _newUser =new User("Matteo","Rosso","NUOVA_EMAIL@gmail.com","Grosseto");
        when(rep.save(any(User.class))).thenReturn(_newUser);
        when(rep.findById(anyLong())).thenReturn(Optional.of(expectedUser));

        final var actual=service.update(anyLong(),_newUser);

        assertThat(actual.getBody()).isEqualTo(_newUser);
        verify(rep, times(1)).save(any(User.class));
        verify(rep, times(1)).findById(anyLong());
        verifyNoMoreInteractions(rep);

    }
}
