package com.java.mydevops;

import org.springframework.context.annotation.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.java.mydevops.entity.Rating;
import com.java.mydevops.mongo.input;
import com.java.mydevops.repository.RatingRepository;


@SpringBootApplication
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
@EnableJpaRepositories(basePackages="com.java.mydevops")
@ComponentScan("com.java.mydevops")
public class MydevopsApplication  implements CommandLineRunner{

    @Autowired
	private RatingRepository ratingRepository;
    
	@Value("${spring.data.mongodb.uri}")
    public String val;
	
	public static void main(String[] args) {
		SpringApplication.run(MydevopsApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
			
		//Ratings collection in mongodb will be updated every hour
		Timer time = new Timer();
		time.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					String uris = val.split("/")[2];
		    		String temp = "";
		    		if(uris.contains("localhost") || uris.contains("192"))
		    			temp = uris.split(":")[0];
		    		else
		    			temp = uris;
					System.out.println("Update : "+LocalDateTime.now())  ;
					input.postRating(ratingRepository.findAll(),temp);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 0,3600000);
		
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

}
