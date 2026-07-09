package com.Gym.System.service;

import com.Gym.System.dto.request.AssessmentRequestDTO;
import com.Gym.System.dto.response.AssessmentResponseDTO;
import com.Gym.System.entity.PhysicalAssessmentEntity;
import com.Gym.System.entity.UserEntity;
import com.Gym.System.enums.SexUser;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.mapper.AssessmentMapper;
import com.Gym.System.repository.PhysicalAssessmentRepository;
import com.Gym.System.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Getter
@Setter
@AllArgsConstructor
public class AssessmentService{

    private final PhysicalAssessmentRepository assessmentRepository;
    private final AssessmentMapper assessmentMapper;
    private final UserRepository userRepository;

    public List<PhysicalAssessmentEntity> findByAll() throws NotFoundException{
        List<PhysicalAssessmentEntity> assessments = assessmentRepository.findAll();

        if(assessments.isEmpty()){
            throw new NotFoundException("Any assessment created on the system");
        }else{
            return assessments;
        }
    }

    public PhysicalAssessmentEntity findById(Long id) throws NotFoundException{
        return assessmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found any assessment with this Id"));
    }

    public PhysicalAssessmentEntity findByUserId(Long userId) throws NotFoundException{
        PhysicalAssessmentEntity assessment = assessmentRepository.findByUser_UserId(userId);

        if(assessment == null){
            throw new NotFoundException("Don't exist any Assessment for this user on the system");
        }else{
            return assessment;
        }
    }

    public Set<AssessmentResponseDTO> findByAllResponse() throws NotFoundException{
        Set<PhysicalAssessmentEntity> assessments = new HashSet<>(findByAll());

        return assessmentMapper.assessmentResponse(assessments);
    }

    public AssessmentResponseDTO findByIdResponse(Long id) throws NotFoundException{
        return assessmentMapper.assessmentResponse(findById(id));
    }

    public AssessmentResponseDTO findByUserIdResponse(Long userId) throws NotFoundException{
        return assessmentMapper.assessmentResponse(findByUserId(userId));
    }

    public AssessmentResponseDTO createPhysicalAssessment(AssessmentRequestDTO assessmentRequest) throws NotFoundException{
        UserEntity user = userRepository.findById(assessmentRequest.getUserId()).orElseThrow(() -> new NotFoundException("This user Id don't exist"));

         if(findByUserId(assessmentRequest.getUserId()) != null)
             assessmentRepository.delete(findByUserId(assessmentRequest.getUserId()));


        int age = Period.between(user.getBirthday() , LocalDate.now()).getYears();
        BigDecimal imc = assessmentRequest.getWeight().divide(assessmentRequest.getWeight().pow(2), 2, RoundingMode.HALF_UP);
        BigDecimal bodyFat;

        if(user.getSexUser() == SexUser.MALE){
            bodyFat = (imc.multiply(BigDecimal.valueOf(1.20))).add(BigDecimal.valueOf((age * 0.23) - 16.2));
        }else{
            bodyFat = (imc.multiply(BigDecimal.valueOf(1.20))).add(BigDecimal.valueOf((age * 0.23) - 5.2));
        }

        PhysicalAssessmentEntity assessment = PhysicalAssessmentEntity.builder()
                .user(user)
                .weight(assessmentRequest.getWeight())
                .height(assessmentRequest.getHeight())
                .bodyFatPercentage(bodyFat)
                .build();
        return assessmentMapper.assessmentResponse(assessment);
    }

    //Colocar Path e Put, adicionar data de criação, e algumas outras funcionalidades para brincar um pouco

    public void deleteAssessment(Long id) throws NotFoundException{
        assessmentRepository.delete(findById(id));
    }
}
