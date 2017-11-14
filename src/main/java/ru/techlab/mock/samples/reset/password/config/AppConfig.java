package ru.techlab.mock.samples.reset.password.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@Configuration
@EnableReactiveMongoRepositories("ru.techlab.mock.samples.reset.password.repository")
@EnableWebFlux
public class AppConfig {}
