package com.java.mydevops.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.mydevops.entity.User;
import com.java.mydevops.repository.UserRepository;
@CrossOrigin(origins = "*")
@RestController
public class UserLoginController {
    @Autowired
    UserRepository userRepository;

    // check if the user exists.
    // If user name exits but password unmatch, return null.
    // if user name does not exist, return null
    // return user only if user name and psw all match
    @GetMapping("/login")
    public User checkPassword(@RequestParam(value = "email") String email,@RequestParam(value = "psw") String psw) {
    	User user = userRepository.emailValidOrNot(email);
        if (user != null) {
            if (user.getPassword().contentEquals(psw)) {
                user.setRatings(null);
                return user;
            }
        }
        User res=new User();
        res.setEmail(null);
        return res;
    }

    // Create a new User
    //return null if user already registered
    @PostMapping("/newUser")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {

        if(userRepository.emailValidOrNot(user.getEmail())!=null){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header", "bad");
            User res=new User();
            res.setEmail(null);
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }

}