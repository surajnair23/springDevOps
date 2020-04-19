package com.java.mydevops.entity;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity(name = "song")
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue
    private Long id;
//consists of song name and artist
//Used to identify songs
@NotBlank
    private String identity;
@OneToMany(targetEntity = Rating.class,mappedBy="song",fetch=FetchType.EAGER)
    private Set<Rating> rating;
@NotBlank
    private String song_name;
@NotBlank
    private String song_artist;
    
    public Song(){
        super();
    }
    public Song(Long id,String song_name, String song_artist) {
        super();
        this.id = id;
        this.song_name = song_name;
        this.song_artist = song_artist;
        this.identity=song_name+"&&"+song_artist;    
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
    

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
    @OneToMany(targetEntity = Song.class,mappedBy="rating",fetch=FetchType.EAGER)
	public Set<Rating> getRating() {
		return rating;
	}
	public void setRating(Set<Rating> rating) {
		this.rating = rating;
	}

    
}