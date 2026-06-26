package com.Gym.System.controller;

import com.Gym.System.dto.request.UserDTO;
import com.Gym.System.entity.UserEntity;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Getter
@Setter
@AllArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserEntity>> findAllUsers() throws NotFoundException{
        return new ResponseEntity<>(userService.findAlll(), HttpStatus.OK);
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<UserEntity> findByName(@PathVariable Long id) throws NotFoundException{
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<UserEntity> findByName(@PathVariable String name) throws NotFoundException {
        return new ResponseEntity<>(userService.findByUserName(name), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserEntity> cadastrarUsuario(@Validated @RequestBody UserDTO userDto){
        return new ResponseEntity<>(userService. createUser(userDto), HttpStatus.OK);
    }
}
