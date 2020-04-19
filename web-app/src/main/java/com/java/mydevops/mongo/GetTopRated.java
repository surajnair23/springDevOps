package com.java.mydevops.mongo;

import com.java.mydevops.mongoEntity.Top;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
public class GetTopRated {
    public static List<Top> run(String val) throws UnknownHostException {
        ServerAddress a=new ServerAddress(InetAddress.getByName(val), 27017);
        MongoClient mongo = new MongoClient(a);
        DB database;
        database=mongo.getDB("ratings");
        DBCollection collection = database.getCollection("ratings");
          String topMap="function() {"+"emit(this.song,this.rate);"+"}"; 
          String topReduce="function(key, values) { " +
          "    return Array.sum(values) / values.length; " +
          "}";
          MapReduceCommand cmd=new MapReduceCommand(collection,topMap,topReduce,null,MapReduceCommand.OutputType.INLINE,null);
          MapReduceOutput nums=collection.mapReduce(cmd);
          List<Top> list = new ArrayList<>();
          for(DBObject o: nums.results()) {
            list.add(new Top(o.get("_id").toString().split("&&")[0],o.get("_id").toString().split("&&")[1],Double.parseDouble(o.get("value").toString())));
        }

        if(list.size()<5){
          int remain = 5-list.size();
          for(int i=0;i<remain;i++){
            list.add(new Top("Null","Null",0));
          }
        }

        Collections.sort(list);

      return list;
          
    }
}