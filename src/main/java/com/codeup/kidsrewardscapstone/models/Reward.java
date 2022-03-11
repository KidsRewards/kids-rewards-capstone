package com.codeup.kidsrewardscapstone.models;

import javax.persistence.*;

@Entity
@Table(name = "rewards")
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String icon;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private long points;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Reward() {
    }

    public Reward(String icon, String title, String body, long points) {
        this.icon = icon;
        this.title = title;
        this.body = body;
        this.points = points;
    }

    public Reward(long id, String icon, String title, String body, long points, User user) {
        this.id = id;
        this.icon = icon;
        this.title = title;
        this.body = body;
        this.points = points;
        this.user = user;
    }

//    public Reward(Reward copy) {
//        id = copy.id;
//        icon = copy.icon;
//        title = copy.title;
//        body = copy.body;
//        points = copy.points;
//        user = copy.user;
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}// end class Reward
