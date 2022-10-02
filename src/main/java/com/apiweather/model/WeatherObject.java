package com.apiweather.model;

import com.apiweather.model.response.TempResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "previsao")
public class WeatherObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_cidade")
    private String nomeCidade;

    @Column(name = "uf")
    private String uf;

    @Column(name = "temperatura_atual")
    private Double temperaturaAtual;

    @Column(name = "temperatura_maxima")
    private Double temperaturaMaxima;

    @Column(name = "temperatura_minima")
    private Double temperaturaMinima;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "data_consulta")
    private LocalDateTime dataConsulta;

    public WeatherObject(TempResponse response) {
        this.nomeCidade = response.getNomeCidade();
        this.uf = response.getUf().toUpperCase();
        this.temperaturaAtual = response.getTemperaturaAtual();
        this.temperaturaMaxima = response.getTemperaturaMaxima();
        this.temperaturaMinima = response.getTempeaturaMinima();
        this.latitude = response.getLatitude();
        this.longitude = response.getLongitude();
        this.dataConsulta = response.getDataConsulta();
    }
}