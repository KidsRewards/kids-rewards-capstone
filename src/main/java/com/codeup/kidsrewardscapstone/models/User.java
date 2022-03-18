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
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private long pointsTotal;

    @Column
    private Boolean isParent;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Task> tasks;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Reward> rewards;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<WishItem> wishItems;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="users_families",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="family_id")}
    )
    private List<Family> families;

    public User(){};

    public User(long id, String username, String email, String password, long pointsTotal) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.pointsTotal = pointsTotal;
    }

    public User(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public User(long id, String username, String email, String password, long pointsTotal, Boolean isParent, List<Task> tasks, List<Reward> rewards, List<WishItem> wishItems, List<Family> families) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.pointsTotal = pointsTotal;
        this.isParent = isParent;
        this.tasks = tasks;
        this.rewards = rewards;
        this.wishItems = wishItems;
        this.families = families;
    }

    public User(User copy){
        id = copy.id;
        username = copy.username;
        email = copy.email;
        password = copy.password;
        pointsTotal = copy.pointsTotal;
        isParent = copy.isParent;
        tasks = copy.tasks;
        rewards = copy.rewards;
        wishItems = copy.wishItems;
        families = copy.families;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getPointsTotal() {
        return pointsTotal;
    }

    public void setPointsTotal(long pointsTotal) {
        this.pointsTotal = pointsTotal;
    }

    public Boolean getParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public void setRewards(List<Reward> rewards) {
        this.rewards = rewards;
    }

    public List<WishItem> getWishItems() {
        return wishItems;
    }

    public void setWishItems(List<WishItem> wishItems) {
        this.wishItems = wishItems;
    }

    public List<Family> getFamilies() {
        return families;
    }

    public void setFamilies(List<Family> families) {
        this.families = families;
    }
}
