package org.lisen.mongdbdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.lisen.mongdbdemo.vo.Runoob;
import org.mongodb.morphia.Datastore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource(name = "datastore")
    private Datastore datastore;

    @GetMapping("/tm")
    public JSONObject tmongo() {

        long s = datastore.getCollection(Runoob.class).count();
        System.out.println(s);

        DBCursor cursor = datastore.getCollection(Runoob.class).find();
        Iterator<DBObject> it = cursor.iterator();
        while(it.hasNext()) {
            DBObject db = it.next();
            String ss = db.get("title").toString();
            System.out.println(ss);
        }

        System.out.println("-------------------------------------------------");

        List<Runoob> datas = datastore.find(Runoob.class).filter("description", "").asList();
        for(Runoob b: datas) {
            System.out.println(b.getDescription());
        }
        List<Runoob> data = datastore.createQuery(Runoob.class).field("description").contains("Neo4j").asList();


        System.out.println("----------------------------------------------");

        if(datastore.getDB().collectionExists("runoob")) {
            DBCollection coll = datastore.getDB().getCollection("runoob");
            Iterator<DBObject> it1 = coll.find().iterator();
            while(it1.hasNext()) {
                DBObject db = it1.next();
                String ss = db.get("title").toString();
                System.out.println(ss);
            }
        }

        JSONObject json = new JSONObject();
        json.put("op", "ok");
        return json;
    }

}
