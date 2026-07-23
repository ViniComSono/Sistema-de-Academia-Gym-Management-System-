package com.Gym.System.service;

import com.Gym.System.dto.request.AssessmentCharacteristicsRequestDTO;
import com.Gym.System.dto.request.AssessmentRequestDTO;
import com.Gym.System.dto.response.AssessmentResponseDTO;
import com.Gym.System.entity.PhysicalAssessmentEntity;
import com.Gym.System.entity.UserEntity;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.mapper.AssessmentMapper;
import com.Gym.System.repository.PhysicalAssessmentRepository;
import com.Gym.System.repository.UserRepository;
import com.Gym.System.util.PhysicalAssessmentCalculator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
    private final PhysicalAssessmentCalculator physicalCalculator;

    public List<PhysicalAssessmentEntity> findByAll() throws NotFoundException{
        List<PhysicalAssessmentEntity> assessments = assessmentRepository.findAll();
        assessments.sort(Comparator.comparing(PhysicalAssessmentEntity::getDate).reversed());

        if(assessments.isEmpty()){
            throw new NotFoundException("Any assessment created on the system");
        }else{
            return assessments;
        }
    }

    public PhysicalAssessmentEntity findById(Long id) throws NotFoundException{
        return assessmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found any assessment with this Id"));
    }

    public List<PhysicalAssessmentEntity> findByUserId(Long userId) throws NotFoundException{
        List<PhysicalAssessmentEntity> assessments = assessmentRepository.findByUser_UserId(userId);
        assessments.sort(Comparator.comparing(PhysicalAssessmentEntity::getDate).reversed());

        if(assessments.isEmpty()){
            throw new NotFoundException("Don't exist any Assessment for this user on the system");
        }else{
            return assessments;
        }
    }

    public PhysicalAssessmentEntity findByMostRecentAssessmentUserId(Long id) throws NotFoundException{
        List<PhysicalAssessmentEntity> assessments = findByUserId(id);
        
        return assessments.getFirst();
    }

    public Set<AssessmentResponseDTO> findByAllResponse() throws NotFoundException{
        Set<PhysicalAssessmentEntity> assessments = new HashSet<>(findByAll());

        return assessmentMapper.assessmentResponse(assessments);
    }

    public AssessmentResponseDTO findByIdResponse(Long id) throws NotFoundException{
        return assessmentMapper.assessmentResponse(findById(id));
    }

    public Set<AssessmentResponseDTO> findByUserIdResponse(Long userId) throws NotFoundException{
        Set<PhysicalAssessmentEntity> assessmentEntitySet = new HashSet<>(findByUserId(userId));
        return assessmentMapper.assessmentResponse(assessmentEntitySet);
    }

    public AssessmentResponseDTO createPhysicalAssessment(AssessmentRequestDTO assessmentRequest) throws NotFoundException{
        UserEntity user = userRepository.findById(assessmentRequest.getUserId()).orElseThrow(() -> new NotFoundException("This user don't exist"));
        AssessmentCharacteristicsRequestDTO characteristicsRequest = AssessmentCharacteristicsRequestDTO.builder()
                .weight(assessmentRequest.getWeight())
                .height(assessmentRequest.getHeight())
                .build();

        PhysicalAssessmentEntity physicalAssessment = PhysicalAssessmentEntity.builder()
                .weight(assessmentRequest.getWeight())
                .height(assessmentRequest.getHeight())
                .bodyFatPercentage(physicalCalculator.calculateBodyFat(assessmentRequest))
                .imc(physicalCalculator.calculateImc(characteristicsRequest))
                .imcType(physicalCalculator.imcType(physicalCalculator.calculateImc(characteristicsRequest)))
                .FatMass(physicalCalculator.calculateFatMass(assessmentRequest))
                .BodyMass(physicalCalculator.calculateLeanBodyMass(assessmentRequest))
                .BasalMetabolicRate(physicalCalculator.calculateBasalMetabolicRate(assessmentRequest))
                .AllDailyEnergyExpenditure(physicalCalculator.calculateAllDailyEnergyExpenditure(assessmentRequest))
                .build();

        user.getAssessmentList().add(physicalAssessment);
        userRepository.save(user);

        assessmentRepository.save(physicalAssessment);
        return assessmentMapper.assessmentResponse(physicalAssessment);
    }

    public void deleteByAssessmentId(Long id) throws NotFoundException{
        PhysicalAssessmentEntity assessment = findById(id);
        assessmentRepository.delete(assessment);
    }

    //Colocar Path e Put, adicionar data de criação, e algumas outras funcionalidades para brincar um pouco

    //deixar na API, mas nãao vejo necessidade de deixar isso 100% explicito também
}
