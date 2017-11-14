package ru.techlab.mock.samples.reset.password.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created by Dmitry.Erohin dim777@ya.ru on 28.03.2017.
 * Copyright (C) 2017 - present by <a href="https://www.ineb.ru/">Ineb Inc</a>.
 * Please see distribution for license.
 */
@Data
@Document
public class User implements Serializable {
    @Id
    @JsonIgnore
    private Object id;

    private String account;

    private String name;

    private String password;

    public static Builder getBuilder() {
        return new User().new Builder();
    }

    public class Builder {

        public Builder account(String account) {
            User.this.account = account;
            return this;
        }

        public Builder name(String name) {
            User.this.name = name;
            return this;
        }

        public Builder password(String password) {
            User.this.password = password;
            return this;
        }

        public User build() {
            return User.this;
        }

    }
}
