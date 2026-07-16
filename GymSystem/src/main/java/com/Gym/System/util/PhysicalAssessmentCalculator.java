package com.Gym.System.util;

import com.Gym.System.dto.request.AssessmentCharacteristicsRequestDTO;
import com.Gym.System.dto.request.AssessmentPhysicalFrequencyRequestDTO;
import com.Gym.System.dto.request.AssessmentRequestDTO;
import com.Gym.System.entity.UserEntity;
import com.Gym.System.enums.Imc;
import com.Gym.System.enums.SexUser;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
@AllArgsConstructor
@Component
public class PhysicalAssessmentCalculator {

    private final UserRepository userRepository;

    public BigDecimal calculateImc (AssessmentCharacteristicsRequestDTO assessmentRequest) throws NotFoundException{
        return assessmentRequest.getWeight().divide(assessmentRequest.getWeight().pow(2), 2, RoundingMode.HALF_UP);
    }

    public Imc imcType (BigDecimal imc){
        double imcDouble = imc.doubleValue();
        if(imcDouble <= 18.5){
            return Imc.underWeight;
        }else if(imcDouble <= 24.9){
            return Imc.normalWeight;
        }else if(imcDouble <= 29.9){
            return Imc.overWeight;
        }else if(imcDouble <= 34.9){
            return Imc.ClassIObesity;
        }else if(imcDouble <= 39.9){
            return Imc.ClassIIObesity;
        }else{
            return Imc.ClassIIIObesity;
        }
    }

    public BigDecimal calculateBodyFat (AssessmentRequestDTO assessmentRequest) throws NotFoundException {
        UserEntity user = userRepository.findById(assessmentRequest.getUserId()).orElseThrow(() -> new NotFoundException("This user Id don't exist"));

        BigDecimal imc = calculateImc(
                AssessmentCharacteristicsRequestDTO.builder()
                        .height(assessmentRequest.getHeight())
                        .weight(assessmentRequest.getWeight())
                        .build());

        int age = Period.between(user.getBirthday() , LocalDate.now()).getYears();
        double genderParam;

        if(user.getSexUser() == SexUser.MALE){
            genderParam = 16.2;
        }else{
            genderParam = 5.4;
        }

        return (imc.multiply(BigDecimal.valueOf(1.20))).add(BigDecimal.valueOf(age).multiply(BigDecimal.valueOf(0.23)).subtract(BigDecimal.valueOf(genderParam)));
    }

    public BigDecimal calculateFatMass (AssessmentRequestDTO assessmentRequest)throws NotFoundException {
        BigDecimal bodyFat = calculateBodyFat(assessmentRequest);

        return assessmentRequest.getWeight().multiply(bodyFat.divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP));
    }

    public BigDecimal calculateLeanBodyMass (AssessmentRequestDTO assessmentRequest)throws NotFoundException {
        return assessmentRequest.getWeight().subtract(calculateFatMass(assessmentRequest));
    }

    public BigDecimal calculateBasalMetabolicRate (AssessmentRequestDTO assessmentRequest) throws NotFoundException {
        UserEntity user = userRepository.findById(assessmentRequest.getUserId()).orElseThrow(() -> new NotFoundException("This user Id don't exist"));

        int age = Period.between(user.getBirthday() , LocalDate.now()).getYears();
        int genderParam;

        if(user.getSexUser() == SexUser.MALE){
            genderParam = 5;
        }else{
            genderParam = -161;
        }

        return BigDecimal.valueOf(10).multiply(assessmentRequest.getWeight())
                .add(BigDecimal.valueOf(6,25))
                .multiply(assessmentRequest.getHeight().multiply(BigDecimal.valueOf(100)))
                .subtract(BigDecimal.valueOf(5))
                .multiply(BigDecimal.valueOf(age))
                .add(BigDecimal.valueOf(genderParam));
    }

    public BigDecimal calculateAllDailyEnergyExpenditure(AssessmentPhysicalFrequencyRequestDTO assessmentRequest) throws NotFoundException{
        AssessmentRequestDTO tmbBuild = AssessmentRequestDTO.builder()
                .userId(assessmentRequest.getUserId())
                .height(assessmentRequest.getHeight())
                .weight(assessmentRequest.getWeight())
                .build();

        BigDecimal tmb = calculateBasalMetabolicRate(tmbBuild);

        return tmb.add(BigDecimal.valueOf(assessmentRequest.getPhysicalActivityFrequency().getFactor()));
    }
}
