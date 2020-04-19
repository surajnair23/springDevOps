package com.java.mydevops.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.mydevops.PLAYLIST;
import com.java.mydevops.dto.EmailDto;
import com.java.mydevops.entity.Feedback;
import com.java.mydevops.entity.Playlist;
import com.java.mydevops.entity.Rating;
import com.java.mydevops.entity.Song;
import com.java.mydevops.mongo.GetAge;
import com.java.mydevops.mongo.GetGender;
import com.java.mydevops.mongo.GetMostRated;
import com.java.mydevops.mongo.GetTopRated;
import com.java.mydevops.mongo.input;
import com.java.mydevops.repository.AdminRepository;
import com.java.mydevops.repository.FeedbackRepository;
import com.java.mydevops.repository.PlaylistRepository;
import com.java.mydevops.repository.RatingRepository;
import com.java.mydevops.repository.SongRepository;
import com.java.mydevops.utility.SendEmail;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

@EnableAutoConfiguration(exclude = { MongoAutoConfiguration.class })
@CrossOrigin(origins = "*")
@RestController
public class AdminController {
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    SongRepository songRepository;
    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    PlaylistRepository playlistRepository;
    
    @Value("${spring.data.mongodb.uri}")
    private String uris;
    
    SendEmail sendEmail = new SendEmail();

    @GetMapping("/allFeedbacks")
    public List<Feedback> feed() {
        List<Feedback> list = feedbackRepository.findAll();
        List<Feedback> res = new ArrayList<>();
        for (Feedback f : list) {
            if (f.getRec() < 60) {
                res.add(f);
            }
        }
        return res;
    }

    @GetMapping("/allRatingsForadmin")
    public List<Rating> rate() {
        List<Rating> list = ratingRepository.findAll();
        return list;
    }

    @GetMapping("/refreshMongo")
    public void init() throws UnknownHostException {
        input.postRating(ratingRepository.findAll(),getCorrectUri(uris.split("/")[2]));
    }

    @GetMapping("/topMongo")
    public List<String> top() throws UnknownHostException {
        Map<Long,Double> map = GetTopRated.run(getCorrectUri(uris.split("/")[2]));
        List<String> list = new ArrayList<>();
        for(long l : map.keySet()){
            Song song = songRepository.findId(l);
            String song_name=song.getSong_name();
            String song_artist=song.getSong_artist();
            list.add(song_name+"|||"+song_artist+"|||"+map.get(l));
        }
        return list;     
    }
    
    @GetMapping("/delPlaylist")
    public ResponseEntity<String> delPlaylist(@RequestParam(value = "key")String key) {
    	if(key==null||key.contentEquals("null")||key.contentEquals(" ")||key.isEmpty()){
            return new ResponseEntity<>("Data Empty",HttpStatus.BAD_REQUEST);
        }else {
        	int ret = playlistRepository.delPlay(key);
        	if(ret>0)
        		return new ResponseEntity<>("Data deleted",HttpStatus.OK);
        	else
        		return new ResponseEntity<>("Data Empty",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/addPlaylist")
    public ResponseEntity<String> addPlaylist(@RequestBody Playlist playlist) {
    	if(playlist != null) {
    		try {
    			playlistRepository.save(playlist);
    			return new ResponseEntity<>("added",HttpStatus.ACCEPTED);
    		}catch(Exception e) {
    			return new ResponseEntity<>("Validation failure, check URL length and type",HttpStatus.UNPROCESSABLE_ENTITY);
    		}
    	}
    	return new ResponseEntity<>("Server error",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/editIt")
    public ResponseEntity<String> editPlaylist(@RequestBody Playlist playlist,@RequestParam(value = "key")Long id) {
    	if(playlist != null) {
    		try {
    			int resp = playlistRepository.updateUrl(playlist.getPlaylist_url(), id.longValue()); //works but does not give result
    			if(resp>0)
    				return new ResponseEntity<>("edited",HttpStatus.ACCEPTED);
    			else {
    				return new ResponseEntity<>("no edit",HttpStatus.UNPROCESSABLE_ENTITY);
    			}
    		}catch(Exception e) {
    			System.out.println(e.getLocalizedMessage());
    			return new ResponseEntity<>("Validation failure, check URL length and type",HttpStatus.UNPROCESSABLE_ENTITY);
    		}
    	}
    	return new ResponseEntity<>("Server error",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @GetMapping("/allPlaylist")
    public ResponseEntity<List<Playlist>> allUrls(){
    	List<Playlist> playlists = new ArrayList<>();
    	playlists.addAll(playlistRepository.getByName(PLAYLIST.Calm.toString()));
    	playlists.addAll(playlistRepository.getByName(PLAYLIST.Exciting.toString()));
    	playlists.addAll(playlistRepository.getByName(PLAYLIST.Happy.toString()));
    	playlists.addAll(playlistRepository.getByName(PLAYLIST.Encouraging.toString()));
    	return new ResponseEntity<>(playlists,HttpStatus.OK);
    }
    
    @GetMapping("/genderMongo")
    public Map<String, Double> gender() throws UnknownHostException {
            System.out.println("test for admin app");
        return GetGender.run(getCorrectUri(uris.split("/")[2]));
    }

    @GetMapping("/ageMongo")
    public Map<String, Double> age() throws UnknownHostException {
//    	System.out.println(val);
    	//System.out.println(env.getProperty("spring.data.mongodb.uri"));
        return GetAge.run(getCorrectUri(uris.split("/")[2]));
    }

    @GetMapping("/countMongo")
    public List<String> count() throws UnknownHostException {
        Map<Long, Double> map = GetMostRated.run(getCorrectUri(uris.split("/")[2]));
        
        List<String> list = new ArrayList<>();
        for(long l : map.keySet()){
            Song song = songRepository.findId(l);
            String song_name=song.getSong_name();
            String song_artist=song.getSong_artist();
            list.add(song_name+"|||"+song_artist+"|||"+map.get(l));
        }
        return list;
    }
    
    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDto emaildto) {
    	try {
    		sendEmail.userApprovedEmail(emaildto);
        	return new ResponseEntity<>("sent",HttpStatus.OK);
    	}catch(Exception e) {
    		return new ResponseEntity<>("error",HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }
    
    @GetMapping("/testMongo")
    public String getMongoHealth() {
    	ServerAddress a;
		try {
			a = new ServerAddress(InetAddress.getByName(getCorrectUri(uris.split("/")[2])), 27017);
			MongoClient mongo = new MongoClient(a);
	        DB database;
	        database=mongo.getDB("ratings");
	    	return "Mongo works";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
    }
    
    public String getCorrectUri(String uris) {
    	if(uris.contains("localhost") || uris.contains("192"))
    		return uris.split(":")[0];
    	else 
    		return uris;
    }
}