package com.java.mydevops.entity;

public class Recommend{
    
    private String song_name;

    private String song_artist;

    private double strength;

    public Recommend(String song_name,String song_artist,double strength2) {
        super();
        this.song_name=song_name;
        this.song_artist=song_artist;
        this.strength=strength2;
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

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }
}