package com.codeup.kidsrewardscapstone.models;


//import org.springframework.scheduling.config.Task;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private long pointsTotal;

    @ManyToOne
    @JoinColumn(name="admin_id")
    private Admin admin;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Task> tasks;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Reward> rewards;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<WishItem> wishItems;

    public User(){}

    public User(long id, String username, String password, Admin admin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public User(String username, String password, Admin admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public User(long id, String username, String password, long pointsTotal, Admin admin, List<Task> tasks, List<Reward> rewards, List<WishItem> wishItems) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.pointsTotal = pointsTotal;
        this.admin = admin;
        this.tasks = tasks;
        this.rewards = rewards;
        this.wishItems = wishItems;
    }

    public User(User copy){
        id = copy.id;
        username = copy.username;
        password = copy.password;
        pointsTotal = copy.pointsTotal;
        admin = copy.admin;
        tasks = copy.tasks;
        rewards = copy.rewards;
        wishItems = copy.wishItems;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
