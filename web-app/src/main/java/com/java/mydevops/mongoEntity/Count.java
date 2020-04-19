package com.java.mydevops.mongoEntity;

public class Count implements Comparable<Count>{
private String song_name;
private String song_artist;
private double count;
public Count(String song_name,String song_artist, double count){
    this.count=count;
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
    return count;
}

public void setCount(double count) {
    this.count = count;
}

@Override
public int compareTo(Count o) {
    // TODO Auto-generated method stub
    return  -(int)Math.round(this.count-o.count);
}


}