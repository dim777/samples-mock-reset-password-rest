package ru.techlab.mock.samples.reset.password.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.techlab.mock.samples.reset.password.model.User;

/**
 * Created by Dmitry.Erohin dim777@ya.ru on 28.03.2017.
 * Copyright (C) 2017 - present by <a href="https://www.ineb.ru/">Ineb Inc</a>.
 * Please see distribution for license.
 */
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByAccount(Mono<String> account);
}
