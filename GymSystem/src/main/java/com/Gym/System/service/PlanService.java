package com.Gym.System.service;

import com.Gym.System.dto.request.PlanPutRequestDTO;
import com.Gym.System.dto.request.PlanRequestDTO;
import com.Gym.System.dto.response.PlanResponseDTO;
import com.Gym.System.entity.PlanEntity;
import com.Gym.System.exception.BadRequestException;
import com.Gym.System.exception.NotFoundException;
import com.Gym.System.mapper.PlanMapper;
import com.Gym.System.repository.PlanRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@AllArgsConstructor
@Getter
public class PlanService {
    private final PlanRepository planRepository;
    private final PlanMapper planMapper;

    public List<PlanEntity> findByDurationInMonths(int months){
        return planRepository.findByPlanDurationInMonths(months);
    }

    public List<PlanEntity> findBySubscriptionId(Long id) throws NotFoundException{
        List<PlanEntity> subscription = planRepository.findBySubscription_SubscriptionId(id);

        if(subscription.isEmpty())
            throw new NotFoundException("This plan don't have any subscription");
        else
            return subscription;
    }

    public List<PlanResponseDTO> findAllResponse(){
        return planMapper.plansResponse(planRepository.findAll());
    }

    public PlanResponseDTO findByPlanIdResponse(Long planId) throws NotFoundException{
        return planMapper.planResponse(planRepository.findById(planId).orElseThrow(() -> new NotFoundException("this plan don't exist")));
    }

    public PlanResponseDTO findByPlanNameResponse(String name) throws NotFoundException{
        PlanEntity plan = planRepository.findByPlanNameIgnoreCase(name);

        if(plan == null)
            throw new NotFoundException("This payment don't exist");
        else
            return planMapper.planResponse(plan);
    }

    public List<PlanResponseDTO> findByDurationInMonthsResponse(int months){
        return planMapper.plansResponse(planRepository.findByPlanDurationInMonths(months));
    }

    public List<PlanResponseDTO> findBySubscriptionIdResponse(Long id) throws NotFoundException{
        List<PlanEntity> subscription = planRepository.findBySubscription_SubscriptionId(id);

        if(subscription.isEmpty())
            throw new NotFoundException("This plan don't have any subscription");
        else
            return planMapper.plansResponse(subscription);
    }

    public PlanResponseDTO createNewPlan(PlanRequestDTO planRequest) throws BadRequestException{
        try{
            if(planRepository.findByPlanNameIgnoreCase(planRequest.getPlanName()) != null){
                PlanEntity plan = PlanEntity.builder()
                        .planName(planRequest.getPlanName())
                        .planPrice(planRequest.getPlanPrice())
                        .planDurationInMonths(planRequest.getPlanDurationInMonths())
                        .build();

                planRepository.save(plan);
                return planMapper.planResponse(plan);
            }else{
                throw new BadRequestException("This user name already exists");
            }
        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }
    }

    //public PlanResponseDTO addSubscriptionOnTheePlan(PlanSubscriptionRequestDTO planRequest) throws BadRequestException, NotFoundException{
    //
    //}

    public PlanResponseDTO editPlan(PlanPutRequestDTO planRequest) throws NotFoundException, BadRequestException{
        try{
            PlanEntity plan = planRepository.findById(planRequest.getPlanId()).orElseThrow(() -> new NotFoundException("this plan don't exist"));

            plan.setPlanName(planRequest.getPlanName());
            plan.setPlanPrice(planRequest.getPlanPrice());
            plan.setPlanDurationInMonths(planRequest.getPlanDurationInMonths());

            planRepository.save(plan);
            return planMapper.planResponse(plan);
        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }
    }

    public void deletePlan(Long planId) throws NotFoundException{
        planRepository.delete(planRepository.findById(planId).orElseThrow(() -> new NotFoundException("this plan don't exist")));
    }
}


