package com.xcdata.verification.config;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ZhiLong Li
 * @Description: com.xcdata.verification.config verification
 * @Date: create in 2020.9.10 13:17
 */
@Configuration
public class MongoConfig extends AbstractMongoConfiguration {
    @Value("${spring.data.mongodb.uri}")
    private String uri;

    @Value("${spring.data.mongodb.database}")
    private String database;
    @Value("${spring.data.mongodb.username}")
    private String username;
    @Value("${spring.data.mongodb.password}")
    private String password;

    @Bean(name = "mongoDbFactory")
    public MongoDbFactory mongoDbFactory() {
        List<String> host = CollectionUtils.arrayToList(uri.split(","));

        // 客户端
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.connectTimeout(10000);
        builder.maxWaitTime(120000);

        //数据库连接池其他参数，如最大连接数这些，可以参考着使用部分参数
        builder.connectionsPerHost(150);
        builder.retryWrites(true);
//        builder.minConnectionsPerHost();
//        builder.requiredReplicaSetName();
//        builder.threadsAllowedToBlockForConnectionMultiplier();
//        builder.serverSelectionTimeout();
//        builder.maxConnectionIdleTime();
//        builder.maxConnectionLifeTime();
//        builder.socketTimeout();
//        builder.socketKeepAlive();
//        builder.sslEnabled());
//        builder.sslInvalidHostNameAllowed();
//        builder.alwaysUseMBeans();
//        builder.heartbeatFrequency();
//        builder.minHeartbeatFrequency();
//        builder.heartbeatConnectTimeout();
//        builder.heartbeatSocketTimeout();
//        builder.localThreshold();

        MongoClientOptions mongoClientOptions = builder.build();

        // MongoDB地址列表,如果有多个ip地址，那么配置文件里面可以用逗号分隔ip地址，这里再把每一个ip地址和端口号添加进list里面
        List<ServerAddress> serverAddresses = new ArrayList<>();
        host.forEach(val ->{
            serverAddresses.add(new ServerAddress(val));
        });

        // 连接认证，如果设置了用户名和密码的话
        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(username, database, password.toCharArray());

        // 创建认证客户端(存在用户名和密码)
        MongoClient mongoClient = new MongoClient(serverAddresses, mongoCredential, mongoClientOptions);

//         创建非认证客户端(没有设置mongodb数据库的用户名和密码)
//        MongoClient mongoClient = new MongoClient(serverAddresses, mongoClientOptions);

        // 创建MongoDbFactory
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, database);
        return mongoDbFactory;
    }

    @Bean(name = "mongoTemplate")
    @Autowired
    public MongoTemplate getMongoTemplate(MongoDbFactory mongoDbFactory){
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);
        return mongoTemplate;
    }

    //事务
    @Bean
    MongoTransactionManager transactionManager(MongoDbFactory mongoDbFactory){
        MongoTransactionManager mtm = new MongoTransactionManager(mongoDbFactory);
        mtm.setDefaultTimeout(1000 * 60 * 30);
        mtm.setTransactionSynchronization(10);
        return mtm;
    }


    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Override
    public MongoClient mongoClient() {
        MongoClientURI mongoUri = new MongoClientURI(uri);
        return new MongoClient(mongoUri);
    }
}
