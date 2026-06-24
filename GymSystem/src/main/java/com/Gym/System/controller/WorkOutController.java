package com.Gym.System.controller;

import com.Gym.System.dto.WorkOutDTO;
import com.Gym.System.dto.WorkOutListDTO;
import com.Gym.System.dto.WorkOutPutDTO;
import com.Gym.System.entity.WorkOutEntity;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.service.WorkOutService;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
@RequestMapping(value = "/treinos")
public class WorkOutController {

    private final WorkOutService workOutService;

    @GetMapping
    public ResponseEntity<List<WorkOutEntity>> findByAll() throws NotFoundException{
        return new ResponseEntity<>(workOutService.findByAll(), HttpStatus.FOUND);
    }

    @GetMapping(value = "/treinoId/{id}")
    public ResponseEntity<WorkOutEntity> findById(@PathVariable Long id) throws NotFoundException{
        return new ResponseEntity<>(workOutService.findById(id), HttpStatus.FOUND);
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<List<WorkOutEntity>> findByUserId(@PathVariable Long userId) throws NotFoundException{
        return new ResponseEntity<>(workOutService.findByUserId(userId), HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<WorkOutEntity> createWorkOut(@RequestBody WorkOutDTO workOutDTO) throws NotFoundException{
        return new ResponseEntity<>(workOutService.createWorkOut(workOutDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<WorkOutEntity> editWorkOut(@RequestBody WorkOutPutDTO workOutPutDTO) throws NotFoundException{
        return new ResponseEntity<>(workOutService.ediWorkOut(workOutPutDTO), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/addExercise")
    public ResponseEntity<WorkOutEntity> addExerciseOnTheWorkOut(@RequestBody WorkOutListDTO workOutListDTO) throws NotFoundException{
        return new ResponseEntity<>(workOutService.addExerciseOnTheWorkOut(workOutListDTO), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/removeExercise")
    public ResponseEntity<WorkOutEntity> removeExerciseOnTheWorkOut(@RequestBody WorkOutListDTO workOutListDTO) throws NotFoundException{
        return new ResponseEntity<>(workOutService.removeExerciseOnTheWorkOut(workOutListDTO), HttpStatus.CREATED);
    }
}
