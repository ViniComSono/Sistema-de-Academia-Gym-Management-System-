package com.Gym.System.controller;

import com.Gym.System.dto.request.UserRequestDTO;
import com.Gym.System.dto.response.UserResponseDTO;
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
import java.util.Set;

@Controller
@Getter
@Setter
@AllArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Set<UserResponseDTO>> findAllUsers() throws NotFoundException{
        return new ResponseEntity<>(userService.findAllResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<UserResponseDTO> findByName(@PathVariable Long id) throws NotFoundException{
        return new ResponseEntity<>(userService.findByIdResponse(id), HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<UserResponseDTO> findByName(@PathVariable String name) throws NotFoundException {
        return new ResponseEntity<>(userService.findByUserNameResponse(name), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Validated @RequestBody UserRequestDTO userRequest){
        return new ResponseEntity<>(userService.createUser(userRequest), HttpStatus.OK);
    }
}
