package com.apiweather.controller;

import com.apiweather.model.response.TempResponse;
import com.apiweather.service.ServiceConsumerOpenApiWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WeatherController {

    @Autowired
    private ServiceConsumerOpenApiWeather testeTemplete;

    @GetMapping("/cidade/{cidade}/uf/{uf}")
    public ResponseEntity<?> getWeather(@PathVariable("cidade") String cidade, @PathVariable("uf") String uf){
        TempResponse tempResponse = this.testeTemplete.serviceSearchTempForCityStateInBrazil(cidade, uf);
        return tempResponse != null ? ResponseEntity.ok(tempResponse) : ResponseEntity.notFound().build();
    }

    @GetMapping("/historico/uf/{uf}")
    public ResponseEntity<?> getListWeatherPorUf(@PathVariable("uf") String uf){
        List<TempResponse> listaTempResponse = this.testeTemplete.findByUf(uf);
        return  !listaTempResponse.isEmpty() ? ResponseEntity.ok(listaTempResponse) : ResponseEntity.noContent().build();
    }

    @GetMapping("/historico/cidade/{nomeCidade}")
    public ResponseEntity<?> getListWeatherPorNomeCidade(@PathVariable("nomeCidade") String nomeCidade){
        List<TempResponse> listaTempResponse = this.testeTemplete.findByNomeCIdade(nomeCidade);
        return  !listaTempResponse.isEmpty() ? ResponseEntity.ok(listaTempResponse) : ResponseEntity.noContent().build();
    }

}
