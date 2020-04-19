package com.java.mydevops.mongo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
public class GetTopRated {
    public static Map<Long, Double> run(String val) throws UnknownHostException {
        ServerAddress a=new ServerAddress(InetAddress.getByName(val), 27017);
        MongoClient mongo = new MongoClient(a);
        DB database;
        database=mongo.getDB("ratings");
        DBCollection collection = database.getCollection("ratings");
          String topMap="function() {"+"emit(this.songId,this.rate);"+"}"; 
          String topReduce="function(key, values) { " +
          "    return Array.sum(values) / values.length; " +
          "}";
          MapReduceCommand cmd=new MapReduceCommand(collection,topMap,topReduce,null,MapReduceCommand.OutputType.INLINE,null);
          MapReduceOutput nums=collection.mapReduce(cmd);
          Map<Long,Double> map = new HashMap<>();
	      for (DBObject dbObject : nums.results()) {
              String res=dbObject.toString();
              long song_id = Long.parseLong(res.split(":")[1].split(",")[0].trim());
              double avg = Double.parseDouble(res.split(":")[2].replace("}", "").trim());
              map.put(song_id, avg);
            }
            LinkedHashMap<Long, Double> reverseSortedMap = new LinkedHashMap<>();
            //Use Comparator.reverseOrder() for reverse orderin
            map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
 
            return reverseSortedMap;
          
    }
}