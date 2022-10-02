package com.apiweather.model;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Main implements Serializable {

    private Double temp;
    private Double feelsLike;
    private Double tempMin;
    private Double tempMax;
    private Double pressure;
    private Double humidity;
    private Double seaLevel;
    private Double grndLevel;

}