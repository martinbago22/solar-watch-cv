package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.dto.RegisterRequestDTO;
import com.codecool.solarwatch.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<String> addUser(@Valid @RequestBody RegisterRequestDTO newUserRequest) {
        return this.userService.createUser(newUserRequest) ?
                new ResponseEntity<>(String.format("USER: [%s] created", newUserRequest.username()),
                        HttpStatus.CREATED) :
                ResponseEntity.badRequest().build();
    }

    // TODO itt mit kell visszaküldeni? jwt string? hogyan köti össze a responseból kapott bármit a kliens böngészője?
    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody RegisterRequestDTO userRequest) {
        String jwt = this.userService.login(userRequest);
        return ResponseEntity.ok(jwt);
    }

    @GetMapping("getAdmin")
    public ResponseEntity<HttpStatusCode> grantAdmin(@RequestParam String userName) {
        this.userService.grantAdminTo(userName);
        return ResponseEntity.ok().build();
    }
}
