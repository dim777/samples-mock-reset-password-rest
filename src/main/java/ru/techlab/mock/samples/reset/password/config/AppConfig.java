package ru.techlab.mock.samples.reset.password.config;

import com.mongodb.MongoClientOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@Configuration
@EnableReactiveMongoRepositories("ru.techlab.mock.samples.reset.password.repository")
@EnableWebFlux
public class AppConfig {
    /*@Bean
    public MongoClientOptions mongoOptions() {
        return MongoClientOptions.builder()
                .minHeartbeatFrequency(200)
                .heartbeatSocketTimeout(30000)
                .socketTimeout(30000)
                .build();
    }*/
}
