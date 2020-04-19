package com.java.mydevops.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.mydevops.entity.Song;
import com.java.mydevops.entity.User;
import com.java.mydevops.mahout.Recommend;
import com.java.mydevops.repository.RatingRepository;
import com.java.mydevops.repository.SongRepository;
import com.java.mydevops.repository.UserRepository;


@CrossOrigin(origins = "*")
@RestController
public class UserRecommendationController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    SongRepository songRepository;

    // get user's rating by song and user entity
    @GetMapping("/getRecommendByUserEntity")
    public List<com.java.mydevops.entity.Recommend> recommend(@Valid @RequestBody User user)
        throws IOException, TasteException {
      List<String> recommendation = Recommend.recommend(user.getId());
      List<com.java.mydevops.entity.Recommend> songs = new ArrayList<com.java.mydevops.entity.Recommend>();
        for(String line : recommendation){
          long song_id=Long.parseLong(line.split("-")[0].trim());
          double strength = Double.parseDouble(line.split("-")[1].trim());
          Song song = songRepository.findId(song_id);
          com.java.mydevops.entity.Recommend rec=new com.java.mydevops.entity.Recommend(song.getSong_name(),song.getSong_artist(),strength);
          songs.add(rec);
      }
      return songs;
    }

    //get user's rating by song and user name
    @GetMapping("/getRecommendByUserName")
    public ResponseEntity<List<com.java.mydevops.entity.Recommend>> recommend2(
        @RequestParam(value = "email") String email)
            throws IOException, TasteException {
        User user = userRepository.emailValidOrNot(email);
        List<String> recommendation = Recommend.recommend(user.getId());
        List<com.java.mydevops.entity.Recommend> songs = new ArrayList<com.java.mydevops.entity.Recommend>();
          for(String line : recommendation){
            long song_id=Long.parseLong(line.split("-")[0].trim());
            double strength = Double.parseDouble(line.split("-")[1].trim());
            Song song = songRepository.findId(song_id);
            com.java.mydevops.entity.Recommend rec=new com.java.mydevops.entity.Recommend(song.getSong_name(),song.getSong_artist(),strength);
            songs.add(rec);
        }
        for(com.java.mydevops.entity.Recommend s:songs){
          System.out.println(s.getSong_name());
        }
        System.out.println(songs.size());
        if(songs.isEmpty()){
          HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header", "bad");
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
        System.out.println("get recommendation");
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }




}