package com.java.mydevops.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity(name = "playlist")
@Table(name = "playlists")
public class Playlist {
    @Id
    @GeneratedValue
    private Long id;

@NotBlank
    private String playlist_name;

@NotBlank
@Size(min = 10, max = 200, message = "url must be between 10 and 200 characters")
    private String playlist_url;


public Playlist(){
        super();
    }
public Playlist(Long id, String playlist_name, String playlist_url) {
        super();
        this.id = id;
        this.playlist_name = playlist_name;
        this.playlist_url = playlist_url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaylist_name() {
        return playlist_name;
    }

    public void setPlaylist_name(String playlist_name) {
        this.playlist_name = playlist_name;
    }

    public String getPlaylist_url() {
        return playlist_url;
    }

    public void setPlaylist_url(String playlist_url) {
        this.playlist_url = playlist_url;
    }

}