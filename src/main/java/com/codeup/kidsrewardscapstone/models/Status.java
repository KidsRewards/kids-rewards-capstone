package com.codeup.kidsrewardscapstone.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="statuses")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String statusDesc;

    @Column(nullable = false)
    private String user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
    private List<Task> tasks;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
    private List<WishItem> wishItems;



    public Status(Long id, String statusDesc) {
        this.id = id;
        this.statusDesc = statusDesc;
    }
    public Status(Long id, String statusDesc, String user) {
        this.id = id;
        this.statusDesc = statusDesc;
        this.user = user;
    }
    public Status(Long id, String statusDesc, List<Task> tasks, List<WishItem> wishItems) {
        this.id = id;
        this.statusDesc = statusDesc;
        this.tasks = tasks;
        this.wishItems = wishItems;
    }

    public Status() {

    }

    public Status(Status copy){
        id = copy.id;
        statusDesc = copy.statusDesc;
        tasks = copy.tasks;
        wishItems = copy.wishItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getUser() {
        return user;
    }
    public void setUser(String user){
        this.user = user;
    }


//    added for USER in Taskcontroller
//@OneToMany(mappedBy = "statuses")
//@JoinColumn(name = "id")
//private User user;
//
//    public User getUser() {
//        return user ;
//    }
//    public void setUser(User user) {
//        this.user = user;
//    }
//for USER in taskController

}
