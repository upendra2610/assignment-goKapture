package com.example.taskManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class User extends BaseModal{
    private String username;
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Task> tasks;
}
