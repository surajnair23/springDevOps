package com.java.mydevops.mongoEntity;

public class Age {
    private String age;
    private double rate;

    public Age(String age, double rate) {
        this.age = age;
        this.rate = rate;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        // TODO Auto-generated method stub
        Age that = (Age) o;
        return this.age.contentEquals(that.age);
    }
}