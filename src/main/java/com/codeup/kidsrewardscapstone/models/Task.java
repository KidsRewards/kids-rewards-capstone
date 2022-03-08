package com.codeup.kidsrewardscapstone.models;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private long points;

    @Column(nullable = false)
    private long due_date;

    @OneToOne
    @JoinColumn (name = "admin_id")
    private Admin admin;

    @OneToOne
    @JoinColumn (name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn (name = "status_id")
    private Status status;

    public Task() {
    }

    public Task(long id, String title, String description, long points, long due_date, Admin admin, User user, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.points = points;
        this.due_date = due_date;
        this.admin = admin;
        this.user = user;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public long getDue_date() {
        return due_date;
    }

    public void setDue_date(long due_date) {
        this.due_date = due_date;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
