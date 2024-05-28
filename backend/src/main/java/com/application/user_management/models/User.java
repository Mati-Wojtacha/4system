package com.application.user_management.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users",
        indexes = {@Index(name = "idx_name_surname_login", columnList = "name, surname, login")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends BaseEntity {

    private String name;

    private String surname;

    private String login;

    public User(Long id, String name, String surname, String login) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.login = login;
    }
}