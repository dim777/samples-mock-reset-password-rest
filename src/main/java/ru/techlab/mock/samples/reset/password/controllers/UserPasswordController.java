package ru.techlab.mock.samples.reset.password.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.techlab.mock.samples.reset.password.model.User;
import ru.techlab.mock.samples.reset.password.model.wrapper.PasswordRequest;
import ru.techlab.mock.samples.reset.password.model.wrapper.results.PasswordChangeResult;
import ru.techlab.mock.samples.reset.password.model.wrapper.results.PwdChangeType;
import ru.techlab.mock.samples.reset.password.repository.UserRepository;

import java.util.Optional;

/**
 * Created by rb052775 on 22.11.2017.
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserPasswordController {
    @Autowired private UserRepository userRepository;

    @PostMapping("/users/pwd")
    public Mono<ResponseEntity<PasswordChangeResult>> updatePassword(@RequestBody final PasswordRequest passwordRequest) {
        User user = userRepository
                .findByAccountBlock(passwordRequest.getAccountId())
                .block();

        if(user == null) return Mono.just(new ResponseEntity<>(new PasswordChangeResult(PwdChangeType.NOT_OK), HttpStatus.OK));
        user.setPassword(passwordRequest.getPassword());

        return userRepository
                .save(user)
                .then(Mono.just(new ResponseEntity<>(new PasswordChangeResult(PwdChangeType.OK), HttpStatus.OK)));
    }
}
