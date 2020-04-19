package com.java.mydevops.mongoEntity;

public class Top implements Comparable<Top>{
    private String song_name;
    private String song_artist;
    private double rate;
    public Top(String song_name, String song_artist, double rate) {
        this.rate=rate;
        this.song_artist=song_artist;
        this.song_name=song_name;
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

    public double getCount() {
        return rate;
    }

    public void setCount(double rate) {
        this.rate = rate;
    }

    @Override
    public int compareTo(Top o) {
    // TODO Auto-generated method stub
    return  -(int)Math.round(this.rate-o.rate);
}

  
}