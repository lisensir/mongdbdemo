package org.lisen.mongdbdemo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Setter
@Getter
@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "im.mongoconfig")
public class MongoConfig {

    private String uri;
    private String dbName;
    private String userName;
    private String password;
    private int connectTimeout=20000;
    private int socketTimeout=20000;
    private int maxWaitTime=20000;


}
