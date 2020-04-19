package com.java.mydevops.mongo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class GetAge {
	
	//@Autowired
	//@Resource
	//public static Environment env;
	
    public static Map<String, Double> run(String val) throws UnknownHostException {
        ServerAddress a=new ServerAddress(InetAddress.getByName(val), 27017);
        MongoClient mongo = new MongoClient(a);
        DB database;
        database=mongo.getDB("ratings");
        DBCollection collection = database.getCollection("ratings");
        String Map="function() {"+"emit(this.age_range,this.rate);"+"};";
		String Reduce="function(key,values) {"+"return Array.sum(values)/values.length;"+"};";
		//String Reduce="function(key,values) {"+"var total = 0;"+"for (var i = 0; i < values.length; i++) {"+"total += values[i].count;}"+"return total;"+"};";
		MapReduceCommand cmd1=new MapReduceCommand(collection,Map,Reduce,null,MapReduceCommand.OutputType.INLINE,null);
        MapReduceOutput nums1=collection.mapReduce(cmd1);
        Map<String,Double> map =new HashMap<>();
		for(DBObject o: nums1.results()) {
            System.out.println(o);
            map.put(o.toString().split(":")[1].split(",")[0].replace(" ", "").replace("\"", "").trim(), Double.parseDouble(o.toString().split(":")[2].replace("}", "").trim()));
        }
        System.out.println("finish admin app test");
        return map;
}
}