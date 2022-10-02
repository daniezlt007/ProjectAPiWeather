package com.apiweather.model.response;

import com.apiweather.model.Coordenada;
import com.apiweather.model.Main;
import com.apiweather.model.WeatherObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
public class TempResponse {

    private Double temperaturaAtual;
    private Double tempeaturaMinima;
    private Double temperaturaMaxima;
    private Double longitude;
    private Double latitude;
    private String nomeCidade;
    private String uf;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime dataConsulta;

    public TempResponse(Main main, Coordenada coord, String nomeCidade, String uf) {
        this.temperaturaAtual = main.getTemp();
        this.tempeaturaMinima = main.getTempMin();
        this.temperaturaMaxima = main.getTempMax();
        this.longitude = coord.getLon();
        this.latitude = coord.getLat();
        this.nomeCidade = nomeCidade;
        this.uf = uf;
        this.dataConsulta = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
    }

    public TempResponse(WeatherObject weatherObject){
        this.temperaturaAtual = weatherObject.getTemperaturaAtual();
        this.tempeaturaMinima = weatherObject.getTemperaturaMinima();
        this.temperaturaMaxima = weatherObject.getTemperaturaMaxima();
        this.longitude = weatherObject.getLongitude();
        this.latitude = weatherObject.getLatitude();
        this.nomeCidade = weatherObject.getNomeCidade();
        this.uf = weatherObject.getUf();
        this.dataConsulta = weatherObject.getDataConsulta();
    }

}
