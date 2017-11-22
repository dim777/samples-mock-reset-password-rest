package ru.techlab.mock.samples.reset.password.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.techlab.mock.samples.reset.password.handlers.UserHandler;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

/**
 * Created by rb052775 on 07.11.2017.
 */
@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> User(UserHandler userHandler){
         return RouterFunctions
                 .route(GET("/users").and(accept(APPLICATION_JSON)), userHandler::fetchUser)
                 .andRoute(GET("/users/hello").and(accept(TEXT_PLAIN)), userHandler::hello)
                 .andRoute(GET("/users").and(accept(APPLICATION_STREAM_JSON)), userHandler::streamUsers)
                 .andRoute(POST("/users").and(accept(APPLICATION_JSON_UTF8)), userHandler::createUser)
                 .andRoute(POST("/users/check"), userHandler::checkUser)
                 .andRoute(POST("/users/json"), userHandler::createUser);
                //.andNest(path("/article/json"), restfulRouter);
    }
}
