package com.java.mydevops.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity(name = "rating")
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue
    private Long id;
@ManyToOne(targetEntity = User.class,fetch=FetchType.EAGER)
@JoinColumn(name="user_name")
    private User user;
@NotBlank
    private String song_name;
@NotBlank
    private String song_artist;
@ManyToOne(targetEntity = Song.class,fetch=FetchType.EAGER)
@JoinColumn(name="identity")
    private Song song;
@Min(value = 1, message = "Rating should not be less than 1")
@Max(value = 5, message = "Rating should not be greater than 5")
    private int rate;
    private String comment;
    

public Rating(){
        super();
    }
public Rating(Long id,User user, String song_name,String song_artist,int rate,String comment) {
        super();
        this.id = id;
        this.user = user;
        this.song_name = song_name;
        this.song_artist = song_artist;
        this.rate = rate;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getSong_name() {
        return song_name;
    }
    

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }
    

    public String getSong_artist() {
        return song_artist;
    }

    public void setSong_artist(String song_artist) {
        this.song_artist = song_artist;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

   
    @ManyToOne(targetEntity = User.class,fetch=FetchType.EAGER)
    @JoinColumn(name="user_name")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

}