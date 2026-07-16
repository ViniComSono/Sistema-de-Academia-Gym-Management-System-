package com.Gym.System.enums;

import lombok.Getter;

@Getter
public enum PhysicalActivityFrequency {
    Sedentary(1.2),
    Light(1.375),
    Moderate(1.55),
    Intense(1.725),
    Athlete(1.9);

    private final double factor;

    PhysicalActivityFrequency(double factor){
        this.factor = factor;
    }
}
