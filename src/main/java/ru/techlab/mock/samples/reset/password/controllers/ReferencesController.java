package ru.techlab.mock.samples.reset.password.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.techlab.mock.samples.reset.password.model.wrapper.ReferenceRequest;
import ru.techlab.mock.samples.reset.password.repository.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by rb052775 on 22.11.2017.
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ReferencesController {
    @Autowired private UserRepository userRepository;

    @Value("${app.pdffile}")
    private String filePath;

    @PostMapping("/references/{type}")
    public Mono<ResponseEntity<byte[]>> getReference(@PathVariable final String type, @RequestBody final ReferenceRequest referenceRequest) throws IOException {
        switch (type){
            case "2ndfl": {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType("application/pdf"));
                byte[] file = Files.readAllBytes(Paths.get(filePath));
                return Mono.just(new ResponseEntity<>(file, headers, HttpStatus.OK));
            }
        }
        return Mono.just(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}
