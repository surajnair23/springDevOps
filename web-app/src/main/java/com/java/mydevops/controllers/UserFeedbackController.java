package com.java.mydevops.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.java.mydevops.entity.Feedback;
import com.java.mydevops.repository.FeedbackRepository;
@CrossOrigin(origins = "*")
@RestController
public class UserFeedbackController {
    @Autowired
    FeedbackRepository feedbackRepository;


    @PostMapping("/fetchList")
    public void getFeedback(@RequestBody Feedback feedback) {
        feedbackRepository.save(feedback);
    }

    @GetMapping("/testHealth")
    public String getHealthCheck() {
    	return "Application seems to be working";
    }
  
}