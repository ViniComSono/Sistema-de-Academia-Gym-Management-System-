package com.Gym.System.controller;

import com.Gym.System.dto.response.AssessmentResponseDTO;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.service.AssessmentService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@Controller
@Getter
@Setter
@AllArgsConstructor
@RequestMapping(value = "/assessment")
public class PhysicalAssessmentController {

    private final AssessmentService assessmentService;

    @GetMapping
    public ResponseEntity<Set<AssessmentResponseDTO>> findAll() throws NotFoundException{
        return new ResponseEntity<>(assessmentService.findByAllResponse(), HttpStatus.FOUND);
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<AssessmentResponseDTO> findById(@PathVariable Long id) throws NotFoundException{
        return new ResponseEntity<>(assessmentService.findByIdResponse(id), HttpStatus.FOUND);
    }

    @GetMapping(value = "/user_id/{id}")
    public ResponseEntity<AssessmentResponseDTO> findByUserId(@PathVariable Long id) throws NotFoundException{
        return new ResponseEntity<>(assessmentService.findByIdResponse(id), HttpStatus.FOUND);
    }
}
