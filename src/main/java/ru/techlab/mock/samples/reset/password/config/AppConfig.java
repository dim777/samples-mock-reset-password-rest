package ru.techlab.mock.samples.reset.password.config;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClientOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@Configuration
@EnableReactiveMongoRepositories("ru.techlab.mock.samples.reset.password.repository")
@EnableWebFlux
public class AppConfig {
    @Value("${spring.data.mongodb.host}")
    private String host;
    @Value("${spring.data.mongodb.port}")
    private String port;
    @Value("${spring.data.mongodb.database}")
    private String dbName;
    @Value("${spring.data.mongodb.username}")
    private String username;
    @Value("${app.changelog}")
    private String changeLog;

    @Autowired private ApplicationContext applicationContext;

    /*@Bean
    public MongoClientOptions mongoOptions() {
        return MongoClientOptions.builder()
                .minHeartbeatFrequency(200)
                .heartbeatSocketTimeout(30000)
                .socketTimeout(30000)
                .build();
    }*/

    @Bean
    public Mongobee mongobee(){
        Mongobee runner = new Mongobee("mongodb://" + host + ":" + port);
        runner.setDbName(dbName);
        runner.setChangeLogsScanPackage(changeLog);
        runner.setSpringApplicationContext(applicationContext);
        return runner;
    }
}
