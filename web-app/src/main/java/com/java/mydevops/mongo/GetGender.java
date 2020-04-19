package com.java.mydevops.mongo;


import com.java.mydevops.mongoEntity.Gender;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class GetGender {
    public static List<Gender> run(String val) throws UnknownHostException {
        ServerAddress a=new ServerAddress(InetAddress.getByName(val), 27017);
        MongoClient mongo = new MongoClient(a);
        DB database;
        database=mongo.getDB("ratings");
        DBCollection collection = database.getCollection("ratings");
        String Map="function() {"+"emit(this.gender,this.rate);"+"};";
        String Reduce="function(key,values) {"+"return Array.sum(values)/values.length;"+"};";
        MapReduceCommand cmd1=new MapReduceCommand(collection,Map,Reduce,null,MapReduceCommand.OutputType.INLINE,null);
        MapReduceOutput nums1=collection.mapReduce(cmd1);
        List<Gender> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        list2.add("female");
        list2.add("male");

        for(DBObject o: nums1.results()) {
            list.add(new Gender(o.get("_id").toString(),Double.parseDouble(o.get("value").toString())));
            list2.remove(o.get("_id").toString());
        }

        for(String range : list2){
            list.add(new Gender(range,0));
        }

   
        return list;   
    }
}