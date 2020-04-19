package com.java.mydevops.mongo;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import org.springframework.beans.factory.annotation.Value;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.java.mydevops.mongoEntity.Age;

public class GetAge {

    @Value("${mongodb.url}")
    private static String mongodbUrl;
    
    public static List<Age> run(String val) throws UnknownHostException {
        
        ServerAddress a=new ServerAddress(InetAddress.getByName(val), 27017);
        MongoClient mongo = new MongoClient(a);
        DB database;
        database=mongo.getDB("ratings");
        DBCollection collection = database.getCollection("ratings");
        String Map="function() {"+"emit(this.age_range,this.rate);"+"};";
		String Reduce="function(key,values) {"+"return Array.sum(values)/values.length;"+"};";
		MapReduceCommand cmd1=new MapReduceCommand(collection,Map,Reduce,null,MapReduceCommand.OutputType.INLINE,null);
        MapReduceOutput nums1=collection.mapReduce(cmd1);
        List<Age> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        list2.add("0-19");
        list2.add("20-29");
        list2.add("30-39");
        list2.add("40-49");
        list2.add("50-59");
        list2.add("60 +");
		for(DBObject o: nums1.results()) {
            System.out.println(o);
            list.add(new Age(o.get("_id").toString(),Double.parseDouble(o.get("value").toString())));
            list2.remove(o.get("_id").toString());
        }

        for(String range : list2){
            list.add(new Age(range,0));
        }

        return list;
}
}