package com.java.mydevops.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;
@Entity(name = "user")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
@NotBlank
    private String fname;
@NotBlank
    private String lname;
@NotBlank
    private String password;
@NotBlank
    private String gender;
    private String city;
    private String state;
    private String zip;
@NotBlank
@Email(message = "Email should be valid")
    private String email;
@Min(value = 18, message = "Age should not be less than 18")
@Max(value = 150, message = "Age should not be greater than 150")
    private int age;
@Size(max = 100, message = "About Me must be between 0 and 100 characters")
    private String aboutMe;
@OneToMany(targetEntity = Rating.class,mappedBy="user",fetch=FetchType.EAGER)
private Set<Rating> ratings = new HashSet<>();	

public User(){
        super();
    }
public User(Long id, String fname,String lname, String password,String gender, String email,int age,String city,String state,String zip,String aboutMe) {
        super();
        this.id = id;
        this.fname = fname;
        this.lname=lname;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.age = age;
        this.zip=zip;
        this.state=state;
        this.city=city;
        this.aboutMe = aboutMe;
    }
public Long getId() {
        return id;
    }
public void setId(Long id) {
        this.id = id;
    }

public String getGender() {
        return gender;
    }
public void setGender(String gender) {
        this.gender = gender;
    }
public String getEmail() {
        return email;
    }
public void setEmail(String email) {
        this.email = email;
    }
public int getAge() {
        return age;
    }
public void setAge(int age) {
        this.age = age;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @OneToMany(targetEntity = Rating.class,mappedBy="user",fetch=FetchType.EAGER)
    public Set<Rating> getRatings() {
        return ratings;
    }
    
    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
}