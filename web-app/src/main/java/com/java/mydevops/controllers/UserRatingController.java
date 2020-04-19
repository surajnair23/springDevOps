package com.java.mydevops.controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.mydevops.entity.Rating;
import com.java.mydevops.entity.Song;
import com.java.mydevops.entity.User;
import com.java.mydevops.repository.RatingRepository;
import com.java.mydevops.repository.SongRepository;
import com.java.mydevops.repository.UserRepository;
@CrossOrigin(origins = "*")
@RestController
public class UserRatingController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    SongRepository songRepository;

    // get user's rating by song and user entity
    @GetMapping("/getRating")
    public Rating getRating(@Valid @RequestBody User user, @RequestParam(value = "song_name") String song_name,
            @RequestParam(value = "song_artist") String song_artist) {
        User my_user = userRepository.emailValidOrNot(user.getEmail());
        Set<Rating> ratings = my_user.getRatings();
        for (Rating rating : ratings) {
            if (rating.getSong().getIdentity().contentEquals(song_name + "&&" + song_artist)) {
                return result(rating);
            }
        }
        return null;
    }

    // get user's rating by song and username
    @GetMapping("/getRating2")
    public int getRating2(@RequestParam(value = "email") String email,
            @RequestParam(value = "song_name") String song_name,
            @RequestParam(value = "song_artist") String song_artist) {
        User user = userRepository.emailValidOrNot(email);
        Set<Rating> ratings = user.getRatings();
        for (Rating rating : ratings) {
            if (rating.getSong().getIdentity().contentEquals(song_name + "&&" + song_artist)) {
                return rating.getRate();
            }
            else if (rating.getSong().getIdentity().contains(song_artist)&&rating.getSong().getIdentity().contains(song_name)) {
                return rating.getRate();
            }
        }
        return -1;
    }

    // get user's all ratings
    @GetMapping("/getUserRatings")
    public ResponseEntity<Set<Rating>> getRating(@RequestParam(value = "email") String email) {
        User user = userRepository.emailValidOrNot(email);
        Set<Rating> ratings = user.getRatings();
        Set<Rating> res = new HashSet<Rating>();
        for (Rating r : ratings) {
            res.add(result(r));
        }
        System.out.println("get rating here");
        if(res.isEmpty()){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header", "bad");
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
        System.out.println("not null");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/getOthersRatings")
    // get other people rating by song name and song artist
    public Set<Rating> getOthersRating(@Valid @RequestBody User user,
            @RequestParam(value = "song_name") String song_name,
            @RequestParam(value = "song_artist") String song_artist) {
        Set<Rating> set = ratingRepository.getBySong(song_name, song_artist);
        for (Rating rating : set) {
            if (rating.getUser().getEmail().contentEquals(user.getEmail())) {
                set.remove(rating);
            }
        }

        Set<Rating> res = new HashSet<Rating>();
        for (Rating r : set) {
            res.add(result(r));
        }
        return res;
    }


    // when receive a new post, update local file to save user id ,song id and
    // rating
    // which will be used for Mahout recommendation
    public void saveToFile(Long long1, Long long2, Long rating) {
        File file = new File("data");
        try {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file, true);
            PrintWriter printWriter = new PrintWriter(fileWriter, true);
            printWriter.println(long1 + "," + long2 + "," + rating);
            printWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void update() throws IOException{
        File file = new File("data");
        try {
			file.createNewFile();
			//rewrite file if app restart
            FileWriter fileWriter = new FileWriter(file, false);
			PrintWriter printWriter = new PrintWriter(fileWriter, true);
			
			List<Rating> ratings = ratingRepository.findAll();
			for(Rating rating:ratings){
				printWriter.println(rating.getUser().getId()+","+rating.getSong().getId()+","+rating.getRate());
			}
            
            printWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 }

    @PostMapping("/editRating")
    public Rating editRaitng(@Valid @RequestBody Rating rating) {

        Optional<Rating> my_rating = ratingRepository.findById(rating.getId());

        Rating edit = my_rating.get();
        edit.setComment(rating.getComment());
        edit.setRate(rating.getRate());

        ratingRepository.save(edit);
        return result(edit);
    }

    // Check if user rated the song
    @GetMapping("/editedOrNot")
    public boolean editOrNot(@RequestParam(value = "email") String email,
            @RequestParam(value = "song_name") String song_name,
            @RequestParam(value = "song_artist") String song_artist) {

        User user = userRepository.emailValidOrNot(email);
        Set<Rating> ratings = user.getRatings();
        for (Rating r : ratings) {
            if (r.getSong().getIdentity().contentEquals(song_name + "&&" + song_artist)) {
                return true;
            }
        }
        return false;
    }

    // Return rating with id,song,rate and comment
    // to simplify the json return result
    public Rating result(Rating rating) {
        Rating result = rating;
        result.setSong(null);
        result.setUser(null);
        return result;
    }

    @DeleteMapping("/deleteRating")
    public String delete(@Valid @RequestBody Rating rating)  {
        Rating rating_1=ratingRepository.findById(rating.getId()).get();
        ratingRepository.delete(rating_1);
        return "ok";
    }

    // Post a new rating
    @PostMapping("/newRating1")
    public Rating createRaitng1(@RequestParam(value = "email") String email,@RequestBody Rating rating) throws IOException {
        System.out.println("new rating 1");
        User user = userRepository.emailValidOrNot(email);
        System.out.println("---------------------"+rating.getRate());
        String song_name=rating.getSong_name();
        String song_artist=rating.getSong_artist();
        String song = song_name + "&&" + song_artist;
        String comment  = rating.getComment();
        int rate = rating.getRate();

        Song user_song = new Song();
        user_song.setIdentity(song);
        user_song.setSong_artist(song_artist);
        user_song.setSong_name(song_name);

        for(Rating r:user.getRatings()){
            if(r.getSong_artist().contentEquals(song_artist)&&r.getSong_name().contentEquals(song_name)){
                r.setComment(comment);
                r.setRate(rate);
                ratingRepository.save(r);
                update();
                return result(r);
            }
        }

        Song newone;
        if (songRepository.findSong(song_name, song_artist).isEmpty()) {
            newone = songRepository.save(user_song);
        } else {
            newone = songRepository.findSong(song_name, song_artist).get(0);
        }

        saveToFile(user.getId(), newone.getId(), (long) rate);



        rating.setSong(newone);
        Set<Rating> set = user.getRatings();
        if (set == null || set.isEmpty()) {
            set = new HashSet<Rating>();
        }
        set.add(rating);

        user.setRatings(set);
        userRepository.save(user);

        rating.setUser(user);
        ratingRepository.save(rating);

        return result(rating);

    }

}