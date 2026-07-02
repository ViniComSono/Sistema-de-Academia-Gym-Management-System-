package com.Gym.System.controller;

import com.Gym.System.dto.request.*;
import com.Gym.System.dto.response.UserResponseDTO;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    public ResponseEntity<Set<UserResponseDTO>> findAllUsers() throws NotFoundException {
        return new ResponseEntity<>(userService.findAllResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<UserResponseDTO> findByName(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(userService.findByIdResponse(id), HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<UserResponseDTO> findByName(@PathVariable String name) throws NotFoundException {
        return new ResponseEntity<>(userService.findByUserNameResponse(name), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequest){
        return new ResponseEntity<>(userService.createUser(userRequest), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> editAllUser(@RequestBody UserPutRequestDTO userRequest) throws NotFoundException {
        return new ResponseEntity<>(userService.editAll(userRequest), HttpStatus.OK);
    }

    @PatchMapping(value = "/addWorkOut")
    public ResponseEntity<UserResponseDTO> addWorkOut(@RequestBody UserWorkOutsRequestDTO userRequest) throws NotFoundException {
        return new ResponseEntity<>(userService.addWorkOut(userRequest), HttpStatus.OK);
    }

    @PatchMapping(value = "/removeWorkOut")
    public ResponseEntity<UserResponseDTO> removeWorkOut(@RequestBody UserWorkOutsRequestDTO userRequest) throws NotFoundException {
        return new ResponseEntity<>(userService.removeWorkOut(userRequest), HttpStatus.OK);
    }

    @PatchMapping(value = "/editUserName")
    public ResponseEntity<UserResponseDTO> editUserName(@RequestBody UserNameRequestDTO userRequest) throws NotFoundException {
        return new ResponseEntity<>(userService.editNameUser(userRequest), HttpStatus.OK);
    }

    @PatchMapping(value = "/editUserCharacteristics")
    public ResponseEntity<UserResponseDTO> editUserCharacteristics(@RequestBody UserCharacteristicsRequestDTO userRequest) throws NotFoundException {
        return new ResponseEntity<>(userService.editUserCharacteristics(userRequest), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) throws NotFoundException{
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
