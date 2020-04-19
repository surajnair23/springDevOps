package com.java.mydevops.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.java.mydevops.PLAYLIST;
import com.java.mydevops.dto.SentenceDto;
import com.java.mydevops.dto.SentimentDto;
import com.java.mydevops.entity.Playlist;
import com.java.mydevops.repository.PlaylistRepository;
import com.java.mydevops.repository.UserRepository;
@CrossOrigin(origins = "*")
@RestController
public class UserPlayListController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PlaylistRepository playlistRepository;
    @Value("${sa.logic.url}")
	private String saLogicApiUrl;

    //request param is searching key
    //get to python app to analyze the key
    //if polirity is >=0.5, recommend some quiet playlist
    //if polirity is <0.5, recommend some exciting playlist
    //if polirity is >=-0.5, recommend some happy playlist
    //if polirity is <-0.5, recommend some encouraging playlist
    @GetMapping("/getPlaylist")
    public ResponseEntity<List<Playlist>> getPlaylist(@RequestParam(value = "key")String key) {
        if(key==null||key.contentEquals("null")||key.contentEquals(" ")||key.isEmpty()){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header", "bad");
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
        System.out.println("key here "+key);
        SentenceDto sentenceDto=new SentenceDto();
        sentenceDto.setSentence(key);
        RestTemplate restTemplate = new RestTemplate();
		SentimentDto sentimentDto = restTemplate.postForEntity(saLogicApiUrl + "/analyse/sentiment", sentenceDto, SentimentDto.class)
                .getBody();
        float polarity = sentimentDto.getPolarity();
        if(polarity>=0.5){
            return new ResponseEntity<>(playlistRepository.getByName(PLAYLIST.Calm.toString()), HttpStatus.OK);
        }
        else if(polarity<0.5&&polarity>0){
            return new ResponseEntity<>(playlistRepository.getByName(PLAYLIST.Exciting.toString()), HttpStatus.OK);
        }
        else if(polarity<=0&&polarity>-0.5){
            return new ResponseEntity<>(playlistRepository.getByName(PLAYLIST.Happy.toString()), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(playlistRepository.getByName(PLAYLIST.Encouraging.toString()), HttpStatus.OK);
        }
    }

    @GetMapping("/getPlaylist1")
    public List<Playlist> demoPlaylist1() {
        return playlistRepository.getByName(PLAYLIST.Calm.toString());
    }
    @GetMapping("/getPlaylist2")
    public List<Playlist> demoPlaylist2() {
        return playlistRepository.getByName(PLAYLIST.Exciting.toString());
    }

    //Used to test when we dont hava admin to init playlists
    @PostMapping("/initPlaylist")
    public void initPlaylist() {
        Playlist p1=new Playlist();
        Playlist p2=new Playlist();
        Playlist p3=new Playlist();
        Playlist p4=new Playlist();

        p1.setPlaylist_name(PLAYLIST.Calm.toString());
        p2.setPlaylist_name(PLAYLIST.Exciting.toString());
        p3.setPlaylist_name(PLAYLIST.Happy.toString());
        p4.setPlaylist_name(PLAYLIST.Encouraging.toString());

        p1.setPlaylist_url("https://youtu.be/XqZsoesa55w");
        p2.setPlaylist_url("https://youtu.be/XqZsoesa55w");
        p3.setPlaylist_url("https://youtu.be/XqZsoesa55w");
        p4.setPlaylist_url("https://youtu.be/XqZsoesa55w");

        playlistRepository.save(p1);
        playlistRepository.save(p2);
        playlistRepository.save(p3);
        playlistRepository.save(p4);
        return;
    }
}