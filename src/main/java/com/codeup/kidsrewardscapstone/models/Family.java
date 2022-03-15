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

    public Family(){};

    public Family(long id, String name, List<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public Family(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
