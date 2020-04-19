package com.java.mydevops.mongoEntity;

public class Gender {
private String gender;
private double rate;
public Gender(String gender, double rate){
    this.gender=gender;
    this.rate=rate;
}

public String getGender() {
    return gender;
}

public void setGender(String gender) {
    this.gender = gender;
}

public double getRate() {
    return rate;
}

public void setRate(double rate) {
    this.rate = rate;
}
}