package com.Gym.System.controller;

import com.Gym.System.dto.request.*;
import com.Gym.System.dto.response.WorkOutResponseDTO;
import com.Gym.System.exception.BadRequestException;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.service.WorkOutService;
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
@RequestMapping(value = "/treinos")
public class WorkOutController {

    private final WorkOutService workOutService;

    @GetMapping
    public ResponseEntity<Set<WorkOutResponseDTO>> findByAll() throws NotFoundException{
        return new ResponseEntity<>(workOutService.findAllResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/treinoId/{id}")
    public ResponseEntity<WorkOutResponseDTO> findById(@PathVariable Long id) throws NotFoundException{
        return new ResponseEntity<>(workOutService.findByIdResponse(id), HttpStatus.FOUND);
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<Set<WorkOutResponseDTO>> findByUserId(@PathVariable Long userId) throws NotFoundException{
        return new ResponseEntity<>(workOutService.findByUserIdResponse(userId), HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<WorkOutResponseDTO> createWorkOut(@RequestBody WorkOutRequestDTO workOutRequest) throws BadRequestException, NotFoundException{
        return new ResponseEntity<>(workOutService.createWorkOut(workOutRequest), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<WorkOutResponseDTO> editWorkOut(@RequestBody WorkOutPutRequestDTO workOutRequest) throws NotFoundException{
        return new ResponseEntity<>(workOutService.editAllWorkOut(workOutRequest), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/editName")
    public ResponseEntity<WorkOutResponseDTO> editWorkOutName(@RequestBody WorkOutNameRequestDTO workOutRequest) throws NotFoundException{
        return new ResponseEntity<>(workOutService.editWorkOutName(workOutRequest), HttpStatus.OK);
    }

    @PatchMapping(value = "/addUser")
    public ResponseEntity<WorkOutResponseDTO> addWorkOutUser(@RequestBody WorkOutUsersRequestDTO workOutRequest) throws NotFoundException{
        return new ResponseEntity<>(workOutService.addWorkOutUsers(workOutRequest), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/removeUser")
    public ResponseEntity<WorkOutResponseDTO> removeWorkOutUser(@RequestBody WorkOutUsersRequestDTO workOutRequest) throws NotFoundException{
        return new ResponseEntity<>(workOutService.removeWorkOutUsers(workOutRequest), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/addExercise")
    public ResponseEntity<WorkOutResponseDTO> addExerciseOnWorkOut(@RequestBody WorkOutExerciseRequestDTO workOutRequest) throws NotFoundException{
        return new ResponseEntity<>(workOutService.addWorkOutExercise(workOutRequest), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/removeExercise")
    public ResponseEntity<WorkOutResponseDTO> removeExerciseOnWorkOut(@RequestBody WorkOutExerciseRequestDTO workOutRequest) throws NotFoundException{
        return new ResponseEntity<>(workOutService.removeWorkOutExercise(workOutRequest), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteWorkOut(@PathVariable Long id) throws NotFoundException{
        workOutService.deleteWorkOut(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
