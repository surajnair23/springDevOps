package com.java.mydevops.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;
@Entity(name = "admin")
@Table(name = "admins")
public class Admin {
    @Id
    @GeneratedValue
    private Long id;
@NotBlank
    private String admin_name;
@NotBlank
    private String password;
@NotBlank
    private String gender;
@NotBlank
@Email(message = "Email should be valid")
    private String email;
@Min(value = 18, message = "Age should not be less than 18")
@Max(value = 150, message = "Age should not be greater than 150")
    private int age;
@Size(min = 10, max = 100, message = "About Me must be between 10 and 100 characters")
    private String aboutMe;

public Admin(){
        super();
    }
public Admin(Long id, String admin_name, String password,String gender, String email,int age,String aboutMe) {
        super();
        this.id = id;
        this.admin_name = admin_name;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.age = age;
        this.aboutMe = aboutMe;
    }
public Long getId() {
        return id;
    }
public void setId(Long id) {
        this.id = id;
    }
public String getAdmin_name() {
        return admin_name;
    }
public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
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
}