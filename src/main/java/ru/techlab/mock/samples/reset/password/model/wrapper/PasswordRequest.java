package ru.techlab.mock.samples.reset.password.model.wrapper;

import lombok.Data;

/**
 * Created by rb052775 on 14.11.2017.
 */
@Data
public class PasswordRequest {
    private String accountId;
    private String password;
}
