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
}
