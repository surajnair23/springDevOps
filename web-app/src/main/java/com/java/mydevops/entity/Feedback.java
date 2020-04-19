package com.java.mydevops.entity;

import javax.persistence.*;

@Entity(name = "feedback")
@Table(name = "feedbacks")
public class Feedback {
    @Id
    @GeneratedValue
    private Long id;
    private double rec;
    private String comment;
    private String email;

    
    public Feedback() {
        super();
    }
    public Feedback(Long id, double rec, String comment,String email) {
        super();
        this.id = id;
        this.rec = rec;
        this.comment = comment;
        this.email=email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getRec() {
        return rec;
    }

    public void setRec(double rec) {
        this.rec = rec;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}