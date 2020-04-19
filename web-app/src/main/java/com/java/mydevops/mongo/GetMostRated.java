package com.java.mydevops.mongo;

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
import java.util.Collections;
import java.util.List;
import com.java.mydevops.mongoEntity.Count;

public class GetMostRated {
    public static List<Count> run(String val) throws UnknownHostException {
        ServerAddress a=new ServerAddress(InetAddress.getByName(val), 27017);
        MongoClient mongo = new MongoClient(a);
        DB database;
        database=mongo.getDB("ratings");
        DBCollection collection = database.getCollection("ratings");
          String topMap="function() {"+"emit(this.song,1);"+"}"; 
          String topReduce="function(key, values) { " +
          "    return Array.sum(values); " +
          "}";
          MapReduceCommand cmd=new MapReduceCommand(collection,topMap,topReduce,null,MapReduceCommand.OutputType.INLINE,null);
          MapReduceOutput nums=collection.mapReduce(cmd);
          List<Count> list = new ArrayList<>();
          for(DBObject o: nums.results()) {
                  list.add(new Count(o.get("_id").toString().split("&&")[0],o.get("_id").toString().split("&&")[1],Double.parseDouble(o.get("value").toString())));
              }

              if(list.size()<5){
                int remain = 5-list.size();
                for(int i=0;i<remain;i++){
                  list.add(new Count("Null","Null",0));
                }
              }

              Collections.sort(list);
 
            return list;
          
    }
}