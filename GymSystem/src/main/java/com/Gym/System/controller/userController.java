package com.Gym.System.controller;

import com.Gym.System.dto.userDTO;
import com.Gym.System.entity.userEntity;
import com.Gym.System.service.userService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Getter
@Setter
@AllArgsConstructor
@RequestMapping(value = "/user")
public class userController {

    private final userService userService;

    @GetMapping
    public ResponseEntity<List<userEntity>> findAllUsers(){
        return new ResponseEntity<>(userService.findAlll(), HttpStatus.OK);
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<userEntity> findByName(@PathVariable Long id){
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<userEntity> findByName(@PathVariable String name){
        return new ResponseEntity<>(userService.findByUserName(name), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<userEntity> cadastrarUsuario(@RequestBody userDTO userDto){
        return new ResponseEntity<>(userService.cadastrarUsuario(userDto), HttpStatus.OK);
    }
}
