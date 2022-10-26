package com.ITCube.database.service;

import com.ITCube.database.exception.ResourceNotFoundException;
import com.ITCube.database.model.User;
import com.ITCube.database.repository.UserRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository rep;
    @InjectMocks
    private UserService service;

    @Test
    void createTest(){
        User u=new User("Matteo","Rosso","matteo@gmail.com","Grosseto");
        when(rep.save(any(User.class))).thenReturn(u);

        final var actual=service.create(new User());

        assertThat(actual).isEqualTo(u);
        verify(rep, times(1)).save(any(User.class));
        verifyNoMoreInteractions(rep);
    }

    @Test
    void byNometest(){

        final var expectedUser=new User("Matteo","Rosso","matteo@gmail.com","Grosseto");
        when(rep.findByNomeIgnoreCase(expectedUser.getNome())).thenReturn(List.of(expectedUser));

        final var actual=service.byNome(expectedUser.getNome()).get(0);

        assertThat(actual).isEqualTo(expectedUser);
        verify(rep, times(1)).findByNomeIgnoreCase(anyString());
        verifyNoMoreInteractions(rep);

    }

    @Test
    void listTest(){

        when(rep.findAll()).thenReturn(List.of(new User()));

        assertFalse(service.list().isEmpty());
        assertThat(service.list()).asList().hasSize(1);
        verify(rep, times(2)).findAll();
        verifyNoMoreInteractions(rep);

    }

    @Test
    void deleteTest(){

        doNothing().when(rep).deleteById(anyLong());

        service.delete(anyLong());
        verify(rep,times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(rep);
    }

    @Test
    void updateTest() throws ResourceNotFoundException {

        User expectedUser=new User("Matteo","Rosso","matteo@gmail.com","Grosseto");
        User _newUser =new User("Matteo","Rosso","NUOVA_EMAIL@gmail.com","Grosseto");
        when(rep.save(any(User.class))).thenReturn(_newUser);
        when(rep.findById(anyLong())).thenReturn(Optional.of(expectedUser));

        final var actual=service.update(anyLong(),_newUser);

        assertThat(actual).isEqualTo(_newUser);
        verify(rep, times(1)).save(any(User.class));
        verify(rep, times(1)).findById(anyLong());
        verifyNoMoreInteractions(rep);

    }

    @Test                           // Verifico effettivo lancio eccezione
    void userNotFound(){

        User _newUser =new User("Matteo","Rosso","NUOVA_EMAIL@gmail.com","Grosseto");
        when(rep.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,()->service.update(2,_newUser));

    }
}
