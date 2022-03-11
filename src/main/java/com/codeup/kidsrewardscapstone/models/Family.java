package com.codeup.kidsrewardscapstone.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="families")
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "families")
    private List<User> users;
}
