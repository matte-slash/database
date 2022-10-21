package com.ITCube.database.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table (name="Utente")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message= "The name is required")
    private String nome;
    @NotBlank(message= "The surname is required")
    private String cognome;
    @NotBlank(message= "The email is required")
    private String email;
    @NotBlank(message= "The city is required")
    @Column(name = "Città")
    private String citta;

    public User(String nome, String cognome, String email, String citta) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.citta=citta;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", citta='" + citta + '\'' +
                '}';
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }
}
