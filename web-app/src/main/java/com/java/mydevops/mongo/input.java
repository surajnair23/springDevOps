package com.java.mydevops.mongo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import com.java.mydevops.entity.Rating;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;



public class input {
    public static void postRating(List<Rating> ratings,String val) throws UnknownHostException {
    ServerAddress a=new ServerAddress(InetAddress.getByName(val), 27017);
    MongoClient mongo = new MongoClient(a);
    DB database;
    database=mongo.getDB("ratings");
    if(database.collectionExists("ratings")){
        DBCollection myCollection = database.getCollection("ratings");
        myCollection.drop();
    }
    database.createCollection("ratings", null);
    DBCollection collection = database.getCollection("ratings");
    for(Rating rating:ratings){
        BasicDBObject document = new BasicDBObject();
	    document.put("Id", rating.getId());
	    document.put("rate", rating.getRate());
		document.put("comment",rating.getComment() );
        document.put("userId", rating.getUser().getId());
        document.put("songId", rating.getSong().getId());
        document.put("song", rating.getSong().getIdentity());
        document.put("gender", rating.getUser().getGender());
        document.put("age", rating.getUser().getAge());
        document.put("age_range", input.range(rating.getUser().getAge()));
        document.put("state", rating.getUser().getState());
		collection.insert(document);	
    }
    mongo.close();
    }
    public static String range(int age) {
        if(age<20){
            return "0-19";
        }
        else if(age>=20&&age<30){
            return "20-29";
        }
        else if(age>=30&&age<40){
            return "30-39";
        }
        else if(age>=40&&age<50){
            return "40-49";
        }
        else if(age>=50&&age<60){
            return "50-59";
        }
        else {
            return "60 +";
        }
    }
}