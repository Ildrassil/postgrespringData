package de.buhl.postgrespringdata.controller;

import de.buhl.postgrespringdata.model.dto.UserRequest;
import de.buhl.postgrespringdata.model.dto.UserResponse;
import de.buhl.postgrespringdata.model.entity.AccountUser;

import de.buhl.postgrespringdata.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RestControllerUserDB {


    private final UserService userService;

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<AccountUser> getAllUser() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserResponse getUser(@PathVariable String id,
    @RequestBody UserRequest userRequest) throws IllegalArgumentException{
        return userService.getUser(id, userRequest);
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public void postUser(@RequestBody UserRequest userRequest) throws IllegalArgumentException{
        userService.createUser(userRequest);
    }

    @PutMapping("/user/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserResponse updateUser(@PathVariable String id,
                                   @RequestBody UserRequest userRequest)
            throws NoSuchElementException{
        return userService.updateUserInfo(id,userRequest);
    }

    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id){
        userService.deleteUser(id);
    }





}
