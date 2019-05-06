package org.lisen.mongdbdemo.config;

import com.mongodb.*;
import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class MongodbAutoConfig {

    private Morphia morphia;

    private MongoClient mongoClient;

    @Resource
    private MongoConfig mongoConfig;

    private Datastore ds;

    private  MongoClientOptions options=null;
    public MongoClientOptions getMongoClientOptions() {
        if(null==options) {
            MongoClientOptions.Builder builder = MongoClientOptions.builder();
            builder.connectTimeout(mongoConfig.getConnectTimeout());
            builder.socketTimeout(mongoConfig.getSocketTimeout());
            builder.maxWaitTime(mongoConfig.getMaxWaitTime());
            builder.heartbeatFrequency(10000);// 心跳频率
            builder.readPreference(ReadPreference.nearest());
            options= builder.build();

        }
        return options;
    }

    @PreDestroy
    public void close() {
        if (null != mongoClient)
            mongoClient.close();
    }

    @Bean(name = "mongoClient", destroyMethod = "close")
    public MongoClient mongo()   {
        try {
            List<ServerAddress> seeds = new ArrayList<>();
            ServerAddress address=null;
            String[] detail = null;
            for (String host : mongoConfig.getUri().split(",")) {
                detail = host.split(":");
                address=new ServerAddress(detail[0],Integer.valueOf(detail[1])) ;
                seeds.add(address);
                System.out.println("MongoDB host    "+address.getHost()+"  port  "+address.getPort());
            }

            MongoClientOptions options= getMongoClientOptions();

            MongoCredential credential =null;
            //是否配置了密码
            if(!StringUtils.isEmpty(mongoConfig.getUserName())&&!StringUtils.isEmpty(mongoConfig.getPassword()))
                credential = MongoCredential.createScramSha1Credential(mongoConfig.getUserName(), mongoConfig.getDbName(),
                        mongoConfig.getPassword().toCharArray());
            if(null==credential)
                mongoClient = new MongoClient(seeds,options);
            else
                mongoClient = new MongoClient(seeds, credential,options);
            return mongoClient;
        } catch (Exception e) {
            e.printStackTrace();
            return mongoClient;
        }

    }


    @Bean(name = "datastore")
    public Datastore getDatastore(MongoClient mongoClient) {
        morphia = new Morphia();
        morphia.mapPackage("org.lisen.imapi.vo");
        ds = morphia.createDatastore(mongoClient, mongoConfig.getDbName());
        ds.ensureIndexes();
        ds.ensureCaps();
        return ds;
    }

}
