package ru.techlab.mock.samples.reset.password.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.techlab.mock.samples.reset.password.model.User;
import ru.techlab.mock.samples.reset.password.model.wrapper.AccountRequest;
import ru.techlab.mock.samples.reset.password.repository.UserRepository;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class UserHandler {
    private static final Logger log = LoggerFactory.getLogger(UserHandler.class);

    @Autowired
    private UserRepository userRepository;

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ok().contentType(TEXT_PLAIN)
                .body(BodyInserters.fromObject("Hello World!"));
    }

    public Mono<ServerResponse> streamUsers(final ServerRequest request) {
        return ok()
                .contentType(APPLICATION_STREAM_JSON)
                .body(userRepository.findAll(), User.class);
    }

    public Mono<ServerResponse> fetchUser(final ServerRequest request) {
        int size = Integer.parseInt(request.queryParam("size").orElse("10"));
        return ok()
                .contentType(APPLICATION_JSON)
                .body(userRepository.findAll().take(size), User.class);
    }

    public Mono<ServerResponse> createUser(final ServerRequest serverRequest) {
        Mono<User> user = serverRequest.bodyToMono(User.class);
        userRepository.insert(user);
        return ServerResponse.ok().build();
    }

    public Mono<ServerResponse> checkUser(final ServerRequest serverRequest) {
        Mono<AccountRequest> accountRequest = serverRequest.bodyToMono(AccountRequest.class);
        Mono<User> user = userRepository
                .findByAccount(accountRequest.map(a -> a.getAccountId()));
        return user.then(ServerResponse.ok().body(user, User.class));
    }
}
