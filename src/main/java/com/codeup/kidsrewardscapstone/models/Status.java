package com.codeup.kidsrewardscapstone.models;

import org.springframework.scheduling.config.Task;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String status_desc;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
    private List<Task> task ;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
    private List<Wishitem> wishitem;

    public Status(Long id, String status_desc) {
        this.id = id;
        this.status_desc = status_desc;
    }

    public Status() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }
}
